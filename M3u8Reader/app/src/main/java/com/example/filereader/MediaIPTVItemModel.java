package com.example.filereader;

public class MediaIPTVItemModel {
    private String group;
    private String logo;
    private String name;
    private String number;
    private String CUID;
    private String country;
    private String language;
    private String radio;

    private String url;

    public MediaIPTVItemModel(String group, String logo, String name, String number, String CUID, String country, String language, String radio, String url) {
        this.group = group;
        this.logo = logo;
        this.name = name;
        this.number = number;
        this.CUID = CUID;
        this.country = country;
        this.language = language;
        this.radio = radio;
        this.url = url;
    }

    @Override
    public String toString() {
        return "MediaIPTVItemModel{url='" + url + "', name=" + name + "}";
    }

    public MediaIPTVItemModel() {

    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCUID() {
        return CUID;
    }

    public void setCUID(String CUID) {
        this.CUID = CUID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
