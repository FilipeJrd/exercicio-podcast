package br.ufpe.cin.if710.podcast;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.text.TextUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.if710.podcast.db.PodcastProviderContract;
import br.ufpe.cin.if710.podcast.domain.ItemFeed;
import br.ufpe.cin.if710.podcast.domain.XmlFeedParser;

/**
 * Created by filipejordao on 15/10/17.
 */

public class RSSDownloadService extends IntentService {
    public RSSDownloadService() {
        super("RSSDownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra("url");
        List<ItemFeed> itemList = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        try {
            itemList = XmlFeedParser.parse(getRssFeed(url));

            if (itemList.size() > 0) {
                for (ItemFeed item: itemList) {
                    ids.add("'"+item.getLink()+"'");
                }

                getContentResolver().delete(PodcastProviderContract.EPISODE_LIST_URI,PodcastProviderContract.EPISODE_LINK+" NOT IN ("+ TextUtils.join(",",ids)+")",null);


                for (ItemFeed item : itemList) {
                    ContentValues values = new ContentValues();

                    values.put(PodcastProviderContract.DATE, item.getPubDate());
                    values.put(PodcastProviderContract.DESCRIPTION, item.getDescription());
                    values.put(PodcastProviderContract.TITLE, item.getTitle());

                    int result = -getContentResolver().update(PodcastProviderContract.EPISODE_LIST_URI, values, PodcastProviderContract.EPISODE_LINK + "=?", new String[]{"'"+item.getLink()+"'"});
                    if (result <= 0){
                        values.put(PodcastProviderContract.EPISODE_LINK, item.getLink());
                        values.put(PodcastProviderContract.EPISODE_POSITION,0);
                        getContentResolver().insert(PodcastProviderContract.EPISODE_LIST_URI, values);
                    }
                }
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("br.ufpe.cin.if710.podcast.FINISHED_DOWNLOAD");
                sendBroadcast(broadcastIntent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private String getRssFeed(String feed) throws IOException {
        InputStream in = null;
        String rssFeed = "";
        try {
            URL url = new URL(feed);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            in = conn.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int count; (count = in.read(buffer)) != -1; ) {
                out.write(buffer, 0, count);
            }
            byte[] response = out.toByteArray();
            rssFeed = new String(response, "UTF-8");
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return rssFeed;
    }
}
