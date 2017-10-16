package br.ufpe.cin.if710.podcast.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.ufpe.cin.if710.podcast.R;
import br.ufpe.cin.if710.podcast.domain.ItemFeed;

public class EpisodeDetailActivity extends Activity {
    private TextView title;
    private TextView description;
    private TextView link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_detail);
        this.title = findViewById(R.id.item_title);
        this.description = findViewById(R.id.item_description);
        this.link = findViewById(R.id.item_link);
        //TODO preencher com informações do episódio clicado na lista...
        Bundle bundle = getIntent().getExtras();
        ItemFeed item = (ItemFeed) bundle.getSerializable("item");
        if(item != null){
            this.description.setText("Description: "+item.getDescription());
            this.title.setText("Title: "+item.getTitle());
            this.link.setText("Link: "+item.getLink());
        }
    }
}
