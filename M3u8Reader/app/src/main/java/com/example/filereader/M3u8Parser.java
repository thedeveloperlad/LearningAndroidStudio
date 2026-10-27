package com.example.filereader;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class M3u8Parser {


    /*public static class ChannelInfo {
        public String title;
        public String url;
        public String duration;
        public String group;
        public String logo;

        public ChannelInfo(String groupTitle, int duration, String name, String url) {
            this.groupTitle = groupTitle;
            this.duration = duration;
            this.name = name;
            this.url = url;
        }

        @Override
        public String toString() {
            return String.format("Channel: %s || URL: %s", title, url);
        }
    }*/

    public List<MediaIPTVItemModel> readM3u8Attributes (String path) throws MalformedURLException {
        //String regex = "([\\\\w-]+)=(\"[^\"]+\"|[^,]+)";
        String regex = "tvg-logo=\\\"(.*?)\\\" group-title=\\\"(.*?)\\\".*,(.+?)$";
        Pattern attrPattern = Pattern.compile(regex);
        List<MediaIPTVItemModel> channels = new ArrayList<>();
        URL url = URI.create(path).toURL();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            //try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            MediaIPTVItemModel currentItem = null;
            int duration = 0;
            String title = "";

            while ((line = br.readLine()) != null) {
                // If the line contains attributes (e.g., #EXT-X-STREAM-INF or #EXTINF)

                line = fixExtinfLine(line);
                Matcher matcher = attrPattern.matcher(line);
                line = line.trim();

                // Skip the header tag
                if (line.isEmpty() || line.equals("#EXTM3U")) {
                    continue;
                }

                if (line.startsWith("#EXTINF:")) {
                    currentItem = new MediaIPTVItemModel();
                    // Extract duration
                    int commaIndex = line.indexOf(',');
                    String extinfInfo = line.substring(8, commaIndex);
                    String[] parts = extinfInfo.split(" ");

                    //currentItem.groupTitle = title;
                    //currentItem.duration = parts[0];

                    // Extract attributes and title using Regex
                    Matcher matcherAttributes = attrPattern.matcher(line);
                    if (matcherAttributes.matches()) {
                        currentItem.setLogo(matcherAttributes.group(1));
                        currentItem.setGroup(matcherAttributes.group(2));
                        currentItem.setName(matcherAttributes.group(3));
                    } else {
                        currentItem.setName(line.substring(commaIndex + 1));
                    }

                    //channels.add(currentItem);
                } else if (currentItem != null && !line.startsWith("#")) {
                    //System.out.println("Entro al ELSE");
                    // It's the URL/File path line
                    //channels.add(new ChannelInfo(title, duration, "name" ,line));

                    currentItem.setUrl(line);
                    channels.add(currentItem);
                    currentItem = null; // Reset for next item
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("  -> channels = " + channels);
        //Log.e("readM3u8Attributes().channels=", channels.toString());
        return channels;
    }

    public static String fixExtinfLine(String inputLine){
        if (inputLine == null || !inputLine.startsWith("#EXTINF:")) {
            return inputLine; // Not an EXTINF line, return as-is
        }

        String cleanedLine = inputLine.replaceAll(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", " ");

        // Clean up any double spaces created by removing commas
        cleanedLine = cleanedLine.replaceAll("\\s+", " ");

        // 2. FIXED REGEX:
        Pattern pattern = Pattern.compile("^(#EXTINF:-?\\d+.*?)\\s+([^\"=]+)$");
        Matcher matcher = pattern.matcher(cleanedLine.trim());

        if (matcher.matches()) {
            String attributes = matcher.group(1).trim();
            String channelTitle = matcher.group(2).trim();

            // Reconstruct with the comma in the exact correct position
            return attributes + "," + channelTitle;
        }

        return inputLine;
    }

    public List<MediaIPTVItemModel> removeOfflineChannels(List<MediaIPTVItemModel> channelList){

        List<MediaIPTVItemModel> onlineChannels = Collections.emptyList();
        ExecutorService executor = Executors.newSingleThreadExecutor();

       /* Future<Boolean> futureResult = executor.submit(() -> {
            try {
                URL url = new URL(channelList.getUrl());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("HEAD");
                connection.setConnectTimeout(4000); // 4 seconds timeout
                connection.setReadTimeout(4000);

                int responseCode = connection.getResponseCode();
                connection.disconnect();

                return (responseCode == HttpURLConnection.HTTP_OK);
            } catch (IOException e) {
                return false;
            }
        }*/
        return onlineChannels;
    }

}
