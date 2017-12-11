package br.ufpe.cin.if710.podcast;

import org.junit.Test;

import br.ufpe.cin.if710.podcast.domain.ItemFeed;

import static org.junit.Assert.*;

/**
 * Created by filipejordao on 10/12/17.
 */

public class ItemFeedTest {

    @Test
    public void itemCreationIsCorrect() throws Exception{
        String title = "title";
        String link = "link";
        String pubDate = "date";
        String description = "description";
        String url = "url";
        int position = 1;

        ItemFeed item = new ItemFeed(title,link,pubDate, description, url, position);
        assertEquals(title, item.getTitle());
        assertEquals(link, item.getLink());
        assertEquals(pubDate, item.getPubDate());
        assertEquals(description, item.getDescription());
        assertEquals(url, item.getUrl());
        assertEquals(position, item.getPosition());
    }
}
