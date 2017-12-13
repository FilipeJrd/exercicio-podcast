package br.ufpe.cin.if710.podcast.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ClipData;

import java.util.List;

import br.ufpe.cin.if710.podcast.domain.ItemFeed;

/**
 * Created by filipejordao on 13/12/17.
 */

public class ItemFeedViewModel extends AndroidViewModel {
    private LiveData<List<ItemFeed>> items;

    public ItemFeedViewModel(Application application) {
        super(application);
    }

    public LiveData<List<ItemFeed>> getItems(){
        if (this.items == null) {
            this.items = PodcastDatabase.getInstance(this.getApplication()).itemFeedDao().getItems();
        }
        return this.items;
    }
}
