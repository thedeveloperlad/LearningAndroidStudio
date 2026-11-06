package com.project.mlbdownloadervideos;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class FileParser {

    String VideoTitle;
    String MLBLink;

    public static String findKey = "window.webMediaPerformanceConfig = {};    window.INIT_DATA =";
    FileParser(){}
    FileParser(String videoTitle, String mlbLink){
        this.VideoTitle = videoTitle;
        this.MLBLink = mlbLink;
    }

    public JSONObject mlbParserFile(String htmlString){
        JSONObject jsonObject = new JSONObject();
        try {

            Document doc = Jsoup.parse(htmlString);

            // 1. Get the content of a <meta name="..."> tag
            // @TestOnly
            // String description = getMetaContentByName(doc, "description");
            // System.out.println("Description: " + description);

            // 2. Get the content of a <meta property="..."> tag (e.g., Open Graph)
            String ogTitle = getMetaContentByProperty(doc, "og:title");
            String ogVideo = getMetaContentByProperty(doc, "og:video");
            String ogImage = getMetaContentByProperty(doc, "og:image");
            String ogDescription = getMetaContentByProperty(doc, "og:description");
            String mlbJsonRaw = getMetaContentByScriptType(doc, "application/ld+json");

            Log.d("MLB JSON= ", mlbJsonRaw);

            JSONObject mlbJsonRawObject = new JSONObject(mlbJsonRaw);
            jsonObject.put("name", ogTitle);
            jsonObject.put("link", ogVideo);
            jsonObject.put("image", ogImage);
            jsonObject.put("description", ogDescription);
            jsonObject.put("mlbJsonRaw", mlbJsonRawObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject milbParserFile(String milbJsonRaw){
        JSONObject jsonObject = new JSONObject();
        try {
            // milbJsonRaw = cleanJsonRaw(milbJsonRaw);
            JSONObject milbJsonRawObject = new JSONObject(cleanJsonRaw(milbJsonRaw)).getJSONObject("clip").getJSONObject("data");
            String ogTitle = milbJsonRawObject.getString("title");

            System.out.println(milbJsonRawObject.getString("title"));
            System.out.println(milbJsonRawObject.getString("description"));
            System.out.println(milbJsonRawObject.getString("type"));
            System.out.println(milbJsonRawObject.getString("url"));

           
            String ogDescription = milbJsonRawObject.getString("description");
            String ogVideo = milbJsonRawObject.getString("url");
            String ogImage = "";
            jsonObject.put("name", ogTitle);
            jsonObject.put("link", ogVideo);
            jsonObject.put("image", ogImage);
            jsonObject.put("description", ogDescription);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Extracts the 'content' attribute from a meta tag with the specified 'name' attribute.
     * @param document The Jsoup Document object.
     * @param name The value of the 'name' attribute (e.g., "description", "keywords").
     * @return The content, or null if not found.
     */
    public static String getMetaContentByName(Document document, String name) {
        // Use a CSS selector to find the specific meta tag
        Element metaTag = document.select("meta[name=" + name + "]").first();
        if (metaTag != null) {
            return metaTag.attr("content");
        }
        return null;
    }

    /**
     * Extracts the 'content' attribute from a meta tag with the specified 'property' attribute.
     * @param document The Jsoup Document object.
     * @param property The value of the 'property' attribute (e.g., "og:title").
     * @return The content, or null if not found.
     */
    public static String getMetaContentByProperty(Document document, String property) {
        // Use a CSS selector to find the specific meta tag
        Element metaTag = document.select("meta[property=" + property + "]").first();
        if (metaTag != null) {
            return metaTag.attr("content");
        }
        return null;
    }

    /*

        <!-- Structured Data Schema -->
        <script type="application/ld+json">

        */
    public static String getMetaContentByScriptType(Document document, String property) {
        // Use a CSS selector to find the specific meta tag
        Element scriptElement = document.select("script[type='" + property + "']").first();

        if(scriptElement != null){
            return scriptElement.data().trim();
        }
        return "";
    }

    void startDownload(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("Downloading File");
        request.setDescription("Please wait...");

        // Save file to the public Downloads folder
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "my_downloaded_file.zip");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager manager;
        //manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Context context = null;
        manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (manager != null) {
            manager.enqueue(request); // Starts the download
        }
    }

    public static String[] getContentByScript(Document document) {
        // Use a CSS selector to find the specific meta tag
        List<String> scriptContents = new ArrayList<>();
        Elements scripts = document.select("script");

        for (Element script : scripts) {
            if (!script.hasAttr("src")) {
                scriptContents.add(script.data());
            }
        }
        return scriptContents.toArray(new String[0]);
    }

    public String cleanJsonRaw(String inputJsonRaw){
        String cleanJson = inputJsonRaw.trim();
        if (cleanJson.startsWith("\"") && cleanJson.endsWith("\"")) {
            cleanJson = cleanJson.substring(1, cleanJson.length() - 1);
        }

        // Replace the escaped quotes (\") with standard quotes (")
        cleanJson = cleanJson.replace("\\\"", "\"");
        return cleanJson;
    }

    public String removeInitData(String milbJsonInitRaw){
        String milbJsonRaw = "";
        milbJsonRaw = milbJsonInitRaw.replace(findKey, "").trim();
        return milbJsonRaw;
    }

    // Extract JSON String raw
    public String getJSONString(String [] milbScriptRaw){
        String milbJsonRaw = "";
        for(int row = 0; row < milbScriptRaw.length; row++){

            if(milbScriptRaw[row].contains(findKey)){
                milbJsonRaw  = milbScriptRaw[row];
                break;
            }
        }
        return milbJsonRaw;
    }

    public Document getHtmlDocument(String htmlString) {
        return Jsoup.parse(htmlString);
    }

    //setters

    //getters
}
