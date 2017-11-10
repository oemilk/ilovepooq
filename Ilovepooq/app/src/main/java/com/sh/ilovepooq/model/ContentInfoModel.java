package com.sh.ilovepooq.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sh.ilovepooq.BR;

public class ContentInfoModel extends BaseObservable {

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

    @Bindable
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
        notifyPropertyChanged(BR.imageURL);
    }

    @Bindable
    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
        notifyPropertyChanged(BR.alt);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
        notifyPropertyChanged(BR.hyperlink);
    }

}
