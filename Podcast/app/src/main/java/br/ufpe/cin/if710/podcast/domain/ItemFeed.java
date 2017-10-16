package br.ufpe.cin.if710.podcast.domain;

import java.io.Serializable;

public class ItemFeed implements Serializable {
    private final String title;
    private final String link;
    private final String pubDate;
    private final String description;
    private  String url;
    private int position;


    public ItemFeed(String title, String link, String pubDate, String description, String url, int position) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.description = description;
        this.url = url;
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getDescription() {
        return description;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUrl() { return url; }
    public void setUrl(String url){this.url = url;}



    @Override
    public String toString() {
        return title;
    }
}