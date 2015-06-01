package org.crazycoder.instagrampopularcollage.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.crazycoder.instagrampopularcollage.Constants;
import org.crazycoder.instagrampopularcollage.InstagramPopularCollage;
import org.crazycoder.instagrampopularcollage.R;
import org.crazycoder.instagrampopularcollage.model.Photo;
import org.crazycoder.instagrampopularcollage.model.RecentFeedResponse;
import org.crazycoder.instagrampopularcollage.model.User;
import org.crazycoder.instagrampopularcollage.network.InstgramService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * CollageActivity
 * <p/>
 * Created by Andrey Dyachkov on 31/05/15.
 */
public class PhotosActivity
        extends AppCompatActivity
        implements Callback<RecentFeedResponse> {

    private static final String TAG_CONTAINER = "PhotosRequestFragment";
    private PhotosGridViewAdapter adapter;
    private PhotosRequestFragment photosRequestFragment;
    @InjectView(R.id.photos)
    GridView photos;
    @InjectView(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        ButterKnife.inject(this);
        adapter = new PhotosGridViewAdapter(this);
        photos.setAdapter(adapter);

        FragmentManager fm = getSupportFragmentManager();
        photosRequestFragment = (PhotosRequestFragment) fm.findFragmentByTag(TAG_CONTAINER);
        if (photosRequestFragment == null) {
            progress.setVisibility(View.GONE);
            photosRequestFragment = new PhotosRequestFragment();
            photosRequestFragment.setArguments(getIntent().getExtras());
            fm.beginTransaction().add(photosRequestFragment, TAG_CONTAINER).commit();
        } else {
            progress.setVisibility(photosRequestFragment.isLoaded() ? View.GONE : View.VISIBLE);
            adapter.addItems(photosRequestFragment.getPhotos());
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.show_preview)
    public void showPreview(View view) {
        ArrayList<Photo> selectedPhotos = adapter.getSelectedPhotos();
        if (selectedPhotos.isEmpty()) {
            new AlertDialog.Builder(InstagramPopularCollage.getInstance()).
                    setTitle(R.string.error).
                    setMessage(R.string.no_photos_selected).show();
            return;
        }
        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putParcelableArrayListExtra(Constants.MODEL, selectedPhotos);
        startActivity(intent);
    }

    @Override
    public void success(RecentFeedResponse recentFeedResponse, Response response) {
        photosRequestFragment.setLoaded(true);
        progress.setVisibility(View.GONE);
        List<RecentFeedResponse.PostItem> items = recentFeedResponse.getPostItems();
        Collections.sort(items);
        for (RecentFeedResponse.PostItem pi : items) {
            Photo p = new Photo(pi.getPostImages().getThumbnailImage().getUrl());
            adapter.addItem(p);
            photosRequestFragment.addPhoto(p);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failure(RetrofitError error) {
        photosRequestFragment.setLoaded(true);
        progress.setVisibility(View.GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.error).setMessage(error.getMessage()).show();
    }
}
