package br.ufpe.cin.if710.podcast.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.if710.podcast.R;
import br.ufpe.cin.if710.podcast.RSSDownloadService;
import br.ufpe.cin.if710.podcast.db.ItemFeedViewModel;
import br.ufpe.cin.if710.podcast.db.PodcastProviderContract;
import br.ufpe.cin.if710.podcast.domain.ItemFeed;
import br.ufpe.cin.if710.podcast.domain.XmlFeedParser;
import br.ufpe.cin.if710.podcast.ui.adapter.XmlFeedAdapter;


public class MainActivity extends AppCompatActivity {

    private RSSDownloadBroadcastReceiver receiver;
    private ItemFeedViewModel viewModel;
    //string do xml
    private final String RSS_FEED = "http://leopoldomt.com/if710/fronteirasdaciencia.xml";
    //TODO teste com outros links de podcast

    private ListView items;
    private LifecycleRegistry lifecycleRegistry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_DayNight);
        setContentView(R.layout.activity_main);
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.markState(Lifecycle.State.CREATED);

        items = (ListView) findViewById(R.id.items);
        XmlFeedAdapter adapter = new XmlFeedAdapter(getApplicationContext(), R.layout.itemlista, new ArrayList<ItemFeed>());

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
        viewModel = ViewModelProviders.of(this).get(ItemFeedViewModel.class);
        viewModel.getItems().observe(this, new Observer<List<ItemFeed>>() {
            @Override
            public void onChanged(@Nullable List<ItemFeed> itemFeeds) {
                if(itemFeeds != null){
                    XmlFeedAdapter adapter = (XmlFeedAdapter) items.getAdapter();
                    adapter.clear();
                    adapter.addAll(itemFeeds);
                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        lifecycleRegistry.markState(Lifecycle.State.RESUMED);
        IntentFilter filter = new IntentFilter("br.ufpe.cin.if710.podcast.FINISHED_DOWNLOAD");
        receiver = new RSSDownloadBroadcastReceiver();
        registerReceiver(receiver,filter);
        if(viewModel.getItems().getValue() != null){
            XmlFeedAdapter adapter = (XmlFeedAdapter) items.getAdapter();
            adapter.addAll(viewModel.getItems().getValue());
        }

        //this.viewModel = ViewModelProviders.of(getApplication()).get(ItemFeedViewModel.class);

    }

    @Override
    @NonNull
    public Lifecycle getLifecycle(){
        return this.lifecycleRegistry;
    }

    @Override
    public void onPause(){
        super.onPause();
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
        lifecycleRegistry.markState(Lifecycle.State.STARTED);
        Intent rssService = new Intent(getApplicationContext(),RSSDownloadService.class);
        rssService.putExtra("url",RSS_FEED);
        startService(rssService);
        //this.displayData();

    }

    @Override
    protected void onStop() {
        super.onStop();
        XmlFeedAdapter adapter = (XmlFeedAdapter) items.getAdapter();
        adapter.clear();
        unregisterReceiver(receiver);
    }

    @SuppressLint("StaticFieldLeak")
    public void displayData(){
        Toast.makeText(getApplicationContext(), "terminando...", Toast.LENGTH_SHORT).show();
        new AsyncTask<Void, Void, List<ItemFeed>>() {
            @Override
            protected List<ItemFeed> doInBackground(Void... params) {
                Cursor cursor = getContentResolver().query(PodcastProviderContract.EPISODE_LIST_URI,PodcastProviderContract.ALL_COLUMNS,null,null,null);
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
                    return feed;
                }
            }

            @Override
            protected void onPostExecute(List<ItemFeed> feed) {
                XmlFeedAdapter adapter = new XmlFeedAdapter(getApplicationContext(), R.layout.itemlista, feed);

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
        }.execute();
    }


    public class RSSDownloadBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("br.ufpe.cin.if710.podcast.FINISHED_DOWNLOAD")){
                //displayData();
            }
        }
    }
}
