package br.ufpe.cin.if710.podcast;

import android.app.Application;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;


public class ExampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Use it only in debug builds
        if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this);
        }
    }
}