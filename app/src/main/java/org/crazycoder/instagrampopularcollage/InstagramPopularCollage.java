package org.crazycoder.instagrampopularcollage;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * InstagramPopularCollage
 * <p/>
 * Created by Andrey Dyachkov on 31/05/15.
 */
public class InstagramPopularCollage extends Application {

    private static InstagramPopularCollage instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static InstagramPopularCollage getInstance() {
        return instance;
    }

}
