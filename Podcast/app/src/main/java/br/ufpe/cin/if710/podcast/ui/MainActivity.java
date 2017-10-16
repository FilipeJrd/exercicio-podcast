package br.ufpe.cin.if710.podcast.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.if710.podcast.R;
import br.ufpe.cin.if710.podcast.RSSDownloadService;
import br.ufpe.cin.if710.podcast.db.PodcastProvider;
import br.ufpe.cin.if710.podcast.db.PodcastProviderContract;
import br.ufpe.cin.if710.podcast.domain.ItemFeed;
import br.ufpe.cin.if710.podcast.domain.XmlFeedParser;
import br.ufpe.cin.if710.podcast.ui.adapter.XmlFeedAdapter;

public class MainActivity extends Activity {

    //ao fazer envio da resolucao, use este link no seu codigo!
    private final String RSS_FEED = "http://leopoldomt.com/if710/fronteirasdaciencia.xml";
    //TODO teste com outros links de podcast

    private ListView items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter("br.ufpe.cin.if710.podcast.FINISHED_DOWNLOAD");
        RSSDownloadBroadcastReceiver receiver = new RSSDownloadBroadcastReceiver();
        registerReceiver(receiver,filter);

        items = (ListView) findViewById(R.id.items);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent rssService = new Intent(getApplicationContext(),RSSDownloadService.class);
        rssService.putExtra("url",RSS_FEED);
        startService(rssService);
    }

    @Override
    protected void onStop() {
        super.onStop();
        XmlFeedAdapter adapter = (XmlFeedAdapter) items.getAdapter();
        adapter.clear();
    }


    public class RSSDownloadBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("br.ufpe.cin.if710.podcast.FINISHED_DOWNLOAD")){
                Toast.makeText(context, "terminando...", Toast.LENGTH_SHORT).show();

                Cursor cursor = context.getContentResolver().query(PodcastProviderContract.EPISODE_LIST_URI,PodcastProviderContract.ALL_COLUMNS,null,null,null);
                List<ItemFeed> feed = new ArrayList<ItemFeed>();
                try {
                    while (cursor.moveToNext()) {
                        String title = cursor.getString(cursor.getColumnIndex(PodcastProviderContract.TITLE));
                        String description = cursor.getString(cursor.getColumnIndex(PodcastProviderContract.DESCRIPTION));
                        String link = cursor.getString(cursor.getColumnIndex(PodcastProviderContract.EPISODE_LINK));
                        String date = cursor.getString(cursor.getColumnIndex(PodcastProviderContract.DATE));
                        String url = cursor.getString(cursor.getColumnIndex(PodcastProviderContract.FILE_URI));
                        int position = cursor.getInt(cursor.getColumnIndex(PodcastProviderContract.EPISODE_POSITION));

                        feed.add(new ItemFeed(title,link,date,description,url,position));
                    }
                } finally {
                    cursor.close();
                    //Adapter Personalizado
                    XmlFeedAdapter adapter = new XmlFeedAdapter(context, R.layout.itemlista, feed);

                    items.setAdapter(adapter);
                    items.setTextFilterEnabled(true);

                    items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            XmlFeedAdapter adapter = (XmlFeedAdapter) parent.getAdapter();
                            ItemFeed item = adapter.getItem(position);
                            //atualizar o list view
                            Intent intent = new Intent(MainActivity.this,EpisodeDetailActivity.class);
                            intent.putExtra("item",item);

                            MainActivity.this.startActivity(intent);
                        }
                    });
                }
            }
        }
    }
}
