package com.example.filereader;

public class MediaItemModel {
    private String title;
    private String time;
    private String category;
    private String status;
    private String link;
    private String language;

    public MediaItemModel(String title, String time, String category, String status, String link, String language) {
        this.title = title;
        this.time = time;
        this.category = category;
        this.status = status;
        this.link = link;
        this.language = language;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /*System.out.printf("title: "+ innerObj.get("title") + " ");//getJSONObject("title"));
                    System.out.printf("time: "+ innerObj.get("time") + " ");
                    System.out.printf("category: "+ innerObj.get("category") + " ");
                    System.out.printf("status: "+ innerObj.get("status") + " ");
                    System.out.printf("link: "+ innerObj.get("link") + " ");
                    System.out.printf("language: "+ innerObj.get("language") + " ");
                    System.out.printf("\n");*/
}
