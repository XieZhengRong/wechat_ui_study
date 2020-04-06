package com.ikudot.wechatuistudy.model;

public class Music {

    private String name;
    private String author;
    private String imageUrl;
    private String musicUrl;
    public Music ( String name,String author,String imageUrl,String musicUrl){

        this.name = name;
        this.author = author;
        this.imageUrl = imageUrl;
        this.musicUrl = musicUrl;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }
}
