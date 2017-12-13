package br.ufpe.cin.if710.podcast.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

import br.ufpe.cin.if710.podcast.domain.ItemFeed;

/**
 * Created by filipejordao on 13/12/17.
 */

@Dao
public interface ItemFeedDao {

    @Query("SELECT * from ItemFeed")
    LiveData<List<ItemFeed>> getItems();


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(ItemFeed item);

    @Update
    int updateItem(ItemFeed item);

    @Query("DELETE FROM ItemFeed")
    int deleteAllItems();
}
