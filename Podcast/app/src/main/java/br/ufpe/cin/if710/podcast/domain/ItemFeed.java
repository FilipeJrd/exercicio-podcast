package br.ufpe.cin.if710.podcast.domain;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

import br.ufpe.cin.if710.podcast.db.PodcastProviderContract;

@Entity(tableName = "ItemFeed")
public class ItemFeed implements Serializable {


    @ColumnInfo(name = PodcastProviderContract.TITLE)
    private final String title;

    @NonNull
    @ColumnInfo(name= PodcastProviderContract.EPISODE_LINK)
    @PrimaryKey
    private final String link;

    @ColumnInfo(name = PodcastProviderContract.DATE)
    private final String pubDate;

    @ColumnInfo(name = PodcastProviderContract.DESCRIPTION)
    private final String description;

    @ColumnInfo(name = PodcastProviderContract.FILE_URI)
    private  String url;

    @ColumnInfo(name = PodcastProviderContract.EPISODE_POSITION)
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


    public String getUrl() { return url; }
    public void setUrl(String url){this.url = url;}



    @Override
    public String toString() {
        return title;
    }
}