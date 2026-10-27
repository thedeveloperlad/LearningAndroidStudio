package com.example.filereader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UrlChecker {

    public static void isUrlOnline(String urlString, UrlCheckCallback callback){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            boolean isOnline = checkLink(urlString);
            // Return the result back to the caller
            callback.onResult(isOnline);
        });
    }

    public static boolean checkLink(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Use HEAD to only pull headers instead of downloading the whole page
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000); // 5 seconds timeout
            connection.setReadTimeout(5000);

            // Get response code from the server
            int responseCode = connection.getResponseCode();
            connection.disconnect();

            // HTTP 200 OK means the link is online and active
            return (responseCode == HttpURLConnection.HTTP_OK);

        } catch (IOException e) {
            // URL is either malformed, offline, or times out
            return false;
        }
    }

    // Callback interface to handle async response
    public interface UrlCheckCallback {
        void onResult(boolean isOnline);
    }
}
