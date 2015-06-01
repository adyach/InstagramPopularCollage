package org.crazycoder.instagrampopularcollage.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.crazycoder.instagrampopularcollage.network.InstgramService;

import retrofit.Callback;

/**
 * RequestFragment
 *
 * Created by Andrey Dyachkov on 01/06/15.
 */
public abstract class RequestFragment extends Fragment {

    private boolean loaded;
    protected Callback callback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (Callback) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        loadData();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    protected abstract void loadData();

}
