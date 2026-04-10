package com.project.mlbdownloadervideos;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class FileParser {

    String VideoTitle;
    String MLBLink;
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

            jsonObject.put("name", ogTitle);
            jsonObject.put("link", ogVideo);
            jsonObject.put("image", ogImage);

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

    //setters

    //getters
}
