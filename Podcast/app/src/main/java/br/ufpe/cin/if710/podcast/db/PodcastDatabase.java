package br.ufpe.cin.if710.podcast.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.ufpe.cin.if710.podcast.domain.ItemFeed;

import android.content.Context;
import android.arch.persistence.room.Room;

/**
 * Created by filipejordao on 13/12/17.
 */
@Database(entities = {ItemFeed.class}, version = 1)

public abstract class PodcastDatabase extends RoomDatabase {

    private static PodcastDatabase instance;

    public abstract ItemFeedDao itemFeedDao();

    public static synchronized PodcastDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), PodcastDatabase.class, "Podcast")
                    .build();
        }
        return instance;
    }
}
