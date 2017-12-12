package br.ufpe.cin.if710.podcast;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.test.ProviderTestCase2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.if710.podcast.db.PodcastProvider;
import br.ufpe.cin.if710.podcast.db.PodcastProviderContract;
import br.ufpe.cin.if710.podcast.domain.ItemFeed;

import static org.junit.Assert.assertEquals;

/**
 * Created by filipejordao on 11/12/17.
 */

@RunWith(AndroidJUnit4.class)
public class PodcastContentProviderTest extends ProviderTestCase2 {
    private ItemFeed[] itemList = {new ItemFeed("title","link","date", "description","url",0)};

    public PodcastContentProviderTest() {
        super(PodcastProvider.class, "br.ufpe.cin.if710.podcast.feed");
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        setContext(InstrumentationRegistry.getTargetContext());
    }

    @After
    @Override
    public void tearDown() throws Exception{
        super.tearDown();
    }



    @Test
    public void insertAndQueryItems_Test() throws Exception {
        this.getMockContentResolver().delete(PodcastProviderContract.EPISODE_LIST_URI,null,null);

        for (ItemFeed item : this.itemList) {
            ContentValues values = new ContentValues();

            values.put(PodcastProviderContract.DATE, item.getPubDate());
            values.put(PodcastProviderContract.DESCRIPTION, item.getDescription());
            values.put(PodcastProviderContract.TITLE, item.getTitle());

            values.put(PodcastProviderContract.EPISODE_LINK, item.getLink());
            values.put(PodcastProviderContract.EPISODE_POSITION,0);

            assertNotNull("Failed to insert data",this.getMockContentResolver().insert(PodcastProviderContract.EPISODE_LIST_URI, values));
        }


        Cursor cursor = this.getMockContentResolver().query(PodcastProviderContract.EPISODE_LIST_URI, PodcastProviderContract.ALL_COLUMNS, null, null, null);
        List<ItemFeed> feed = new ArrayList<>();
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(PodcastProviderContract.TITLE));
            String description = cursor.getString(cursor.getColumnIndex(PodcastProviderContract.DESCRIPTION));
            String link = cursor.getString(cursor.getColumnIndex(PodcastProviderContract.EPISODE_LINK));
            String date = cursor.getString(cursor.getColumnIndex(PodcastProviderContract.DATE));
            String url = cursor.getString(cursor.getColumnIndex(PodcastProviderContract.FILE_URI));
            int position = cursor.getInt(cursor.getColumnIndex(PodcastProviderContract.EPISODE_POSITION));

            feed.add(new ItemFeed(title, link, date, description, url, position));
        }

        assertEquals("The amount of items on the database differ from the input",itemList.length, feed.size());
        for (int i = 0; i < 0 ; i++ ){
            ItemFeed item = feed.get(i);
            ItemFeed correctItem = itemList[i];

            assertEquals(correctItem.getTitle(), item.getTitle());
            assertEquals(correctItem.getDescription(),item.getDescription());
            assertEquals(correctItem.getLink(),item.getLink());
            assertEquals(correctItem.getPubDate(),item.getPubDate());
            assertEquals(correctItem.getPosition(),item.getPosition());
        }
        cursor.close();
    }
}
