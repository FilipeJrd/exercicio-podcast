package br.ufpe.cin.if710.podcast.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class PodcastProvider extends ContentProvider {
    private PodcastDBHelper dbHelper;


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.

        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        if (uri.equals(PodcastProviderContract.EPISODE_LIST_URI)) {
            return db.delete(PodcastProviderContract.EPISODE_TABLE,selection,selectionArgs);
        }else{
            return -1;
        }
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        if (uri.equals(PodcastProviderContract.EPISODE_LIST_URI)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(PodcastProviderContract.EPISODE_TABLE,null,values);
        }
        return uri;
    }

    @Override
    public boolean onCreate() {

        this.dbHelper = PodcastDBHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        if (uri.equals(PodcastProviderContract.EPISODE_LIST_URI)){
            return db.query(PodcastProviderContract.EPISODE_TABLE,projection,selection,selectionArgs,null,null,sortOrder);
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        if (uri.equals(PodcastProviderContract.EPISODE_LIST_URI)){
            return db.update(PodcastProviderContract.EPISODE_TABLE,values,selection,selectionArgs);
        }
        return -1;
    }
}
