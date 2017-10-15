package br.ufpe.cin.if710.podcast.ui.adapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.ufpe.cin.if710.podcast.R;
import br.ufpe.cin.if710.podcast.domain.ItemFeed;

public class XmlFeedAdapter extends ArrayAdapter<ItemFeed> {
    static private MediaPlayer player = new MediaPlayer();
    int linkResource;

    public XmlFeedAdapter(Context context, int resource, List<ItemFeed> objects) {
        super(context, resource, objects);
        linkResource = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(getContext(), linkResource, null);
            holder = new ViewHolder();
            holder.item_title = (TextView) convertView.findViewById(R.id.item_title);
            holder.item_date = (TextView) convertView.findViewById(R.id.item_date);
            holder.item_action = (Button) convertView.findViewById(R.id.item_action);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_title.setText(getItem(position).getTitle());
        holder.item_date.setText(getItem(position).getPubDate());
        holder.item_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "baixando...", Toast.LENGTH_SHORT).show();

                class DownloadFile extends AsyncTask<String, Integer, Boolean> {
                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        if (aBoolean) {
                            Toast.makeText(getContext(), "baixou...", Toast.LENGTH_SHORT).show();

                            holder.item_action.setText("Play");
                            holder.item_action.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String filename = getItem(position).getTitle()+".mp3";
                                    File file = new File(getContext().getFilesDir(),filename);
                                    try {
                                        if (player.isPlaying()) {
                                            player.stop();
                                            player =  new MediaPlayer();
                                        }
                                        player.setDataSource(file.getAbsolutePath());
                                        player.prepare();
                                        player.start();
                                    } catch (IOException e) {
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getContext(), "falhou...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected Boolean doInBackground(String... sUrl) {
                            InputStream input = null;
                            OutputStream output = null;
                            HttpURLConnection connection = null;
                            try {
                                URL url = new URL(getItem(position).getLink());
                                connection = (HttpURLConnection) url.openConnection();
                                connection.connect();

                                // expect HTTP 200 OK, so we don't mistakenly save error report
                                // instead of the file
                                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                                   return false;
                                }

                                // this will be useful to display download percentage
                                // might be -1: server did not report the length
                                int fileLength = connection.getContentLength();

                                // download the file
                                input = connection.getInputStream();
                                String filename = getItem(position).getTitle()+".mp3";
                                File file = new File(getContext().getFilesDir(),filename);
                                if (file.exists()){
                                    file.delete();
                                }
                                file.createNewFile();

                                output = new FileOutputStream(file);

                                byte data[] = new byte[4096];
                                long total = 0;
                                int count;
                                while ((count = input.read(data)) != -1) {
                                    // allow canceling with back button
                                    if (isCancelled()) {
                                        input.close();
                                        return null;
                                    }
                                    total += count;
                                    // publishing the progress....
                                    if (fileLength > 0) // only if total length is known
                                        publishProgress((int) (total * 100 / fileLength));
                                    output.write(data, 0, count);
                                }
                            return true;
                        } catch (Exception e) {
                            return false;
                        }
                    }
                }

                new DownloadFile().execute("");
            }
        });

        return convertView;
    }

    /**
     * public abstract View getView (int position, View convertView, ViewGroup parent)
     * <p>
     * Added in API level 1
     * Get a View that displays the data at the specified position in the data set. You can either create a View manually or inflate it from an XML layout file. When the View is inflated, the parent View (GridView, ListView...) will apply default layout parameters unless you use inflate(int, android.view.ViewGroup, boolean) to specify a root view and to prevent attachment to the root.
     * <p>
     * Parameters
     * position	The position of the item within the adapter's data set of the item whose view we want.
     * convertView	The old view to reuse, if possible. Note: You should check that this view is non-null and of an appropriate type before using. If it is not possible to convert this view to display the correct data, this method can create a new view. Heterogeneous lists can specify their number of view types, so that this View is always of the right type (see getViewTypeCount() and getItemViewType(int)).
     * parent	The parent that this view will eventually be attached to
     * Returns
     * A View corresponding to the data at the specified position.
     */


	/*
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.itemlista, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.item_title);
		textView.setText(items.get(position).getTitle());
	    return rowView;
	}
	/**/

    //http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
    static class ViewHolder {
        TextView item_title;
        TextView item_date;
        Button item_action;
    }
}
