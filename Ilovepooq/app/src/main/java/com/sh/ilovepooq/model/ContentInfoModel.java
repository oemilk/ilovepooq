package com.sh.ilovepooq.model;

public class ContentInfoModel {

    private String imageURL;
    private String alt;
    private String title;
    private String hyperlink;

    public ContentInfoModel(String imageURL, String alt, String title, String hyperlink) {
        this.imageURL = imageURL;
        this.alt = alt;
        this.title = title;
        this.hyperlink = hyperlink;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

}
