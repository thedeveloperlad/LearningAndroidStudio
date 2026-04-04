package com.project.mlbdownloadervideos;

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

            jsonObject.put("name", ogTitle);
            jsonObject.put("link", ogVideo);

        } catch (org.json.JSONException e) {
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

    //setters

    //getters
}
