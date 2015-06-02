package org.crazycoder.instagrampopularcollage.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import org.crazycoder.instagrampopularcollage.Constants;
import org.crazycoder.instagrampopularcollage.InstagramPopularCollage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit.Callback;

/**
 * PreviewCompressorFragment
 * <p/>
 * Created by Andrey Dyachkov on 02/06/15.
 */
public class PreviewCompressorFragment extends Fragment {

    private boolean loaded;
    private Bitmap bitmap;
    private Callback callback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (Callback) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        bitmap = getArguments().getParcelable(Constants.BITMAP);
        new Compressor().execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
        bitmap = null;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public interface Callback {
        public void onFinished(Uri uri);
    }

    private class Compressor extends AsyncTask<Void, Void, Uri> {

        @Override
        protected Uri doInBackground(Void... params) {
            try {
                File path = new File(getActivity().getCacheDir(), Constants.CACHE_DIR);
                path.mkdir();
                File file = new File(path, Constants.COLLAGE_NAME);
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, new FileOutputStream(file));
                return Uri.fromFile(file);
            } catch (IOException e) {
                Log.d("PreviewCompressor", "upps...");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Uri uri) {
            loaded = true;
            callback.onFinished(uri);
        }
    }
}
