package com.sh.ilovepooq.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchImageModel {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("documents")
    @Expose
    private List<Document> documents = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public class Meta {

        @SerializedName("total_count")
        @Expose
        private Integer totalCount;
        @SerializedName("pageable_count")
        @Expose
        private Integer pageableCount;
        @SerializedName("is_end")
        @Expose
        private Boolean isEnd;

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public Integer getPageableCount() {
            return pageableCount;
        }

        public void setPageableCount(Integer pageableCount) {
            this.pageableCount = pageableCount;
        }

        public Boolean getIsEnd() {
            return isEnd;
        }

        public void setIsEnd(Boolean isEnd) {
            this.isEnd = isEnd;
        }

    }

    public class Document {

        @SerializedName("collection")
        @Expose
        private String collection;
        @SerializedName("thumbnail_url")
        @Expose
        private String thumbnailUrl;
        @SerializedName("image_url")
        @Expose
        private String imageUrl;
        @SerializedName("width")
        @Expose
        private Integer width;
        @SerializedName("height")
        @Expose
        private Integer height;
        @SerializedName("display_sitename")
        @Expose
        private String displaySitename;
        @SerializedName("doc_url")
        @Expose
        private String docUrl;
        @SerializedName("datetime")
        @Expose
        private String datetime;

        public String getCollection() {
            return collection;
        }

        public void setCollection(String collection) {
            this.collection = collection;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public String getDisplaySitename() {
            return displaySitename;
        }

        public void setDisplaySitename(String displaySitename) {
            this.displaySitename = displaySitename;
        }

        public String getDocUrl() {
            return docUrl;
        }

        public void setDocUrl(String docUrl) {
            this.docUrl = docUrl;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

    }

}