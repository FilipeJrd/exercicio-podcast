package br.ufpe.cin.if710.podcast;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.text.TextUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

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
        RSSDownloader downloader = new RSSDownloader();

        boolean success = downloader.startDownload(url, this);

        if (success) {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("br.ufpe.cin.if710.podcast.FINISHED_DOWNLOAD");
            sendBroadcast(broadcastIntent);
        }
    }
}
