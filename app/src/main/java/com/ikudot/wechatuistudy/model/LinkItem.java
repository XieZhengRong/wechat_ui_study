package com.ikudot.wechatuistudy.model;

public class LinkItem {
    private String imageUrl;
    private String linkUrl;
    private String title;

    public LinkItem(String imageUrl, String linkUrl, String title) {
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
