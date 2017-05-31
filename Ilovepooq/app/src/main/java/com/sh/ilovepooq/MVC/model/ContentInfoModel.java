package com.sh.ilovepooq.MVC.model;

public class ContentInfoModel {

    private String imageURL;
    private String alt;
    private String title;
    private String hyperlink;
    private int loadingResult;

    public ContentInfoModel(String imageURL, String alt, String title, String hyperlink, int loadingResult) {
        this.imageURL = imageURL;
        this.alt = alt;
        this.title = title;
        this.hyperlink = hyperlink;
        this.loadingResult = loadingResult;
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

    public int getLoadingResult() {
        return loadingResult;
    }

    public void setLoadingResult(int loadingResult) {
        this.loadingResult = loadingResult;
    }

}
