package org.crazycoder.instagrampopularcollage.controller;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.crazycoder.instagrampopularcollage.Constants;
import org.crazycoder.instagrampopularcollage.R;
import org.crazycoder.instagrampopularcollage.model.Photo;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * PreviewActivity
 * <p/>
 * Created by Andrey Dyachkov on 31/05/15.
 */
public class PreviewActivity
        extends AppCompatActivity
        implements PreviewCompressorFragment.Callback {

    private static final String TAG_CONTAINER = "PreviewCompressorFragment";
    private PreviewCompressorFragment previewCompressorFragment;
    @InjectView(R.id.preview)
    LinearLayout preview;
    @InjectView(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.inject(this);
        for (Photo p : Photo.getPhotos()) {
            ImageView image = new ImageView(this);
            image.setImageBitmap(p.getBitmap());
            preview.addView(image);
        }

        FragmentManager fm = getSupportFragmentManager();
        previewCompressorFragment = (PreviewCompressorFragment) fm.findFragmentByTag(TAG_CONTAINER);
        if (previewCompressorFragment == null) {
            progress.setVisibility(View.GONE);
        } else {
            progress.setVisibility(previewCompressorFragment.isLoaded() ? View.GONE : View.VISIBLE);
        }
    }

    @OnClick(R.id.send)
    public void sendCollage(View view) {
        preview.setDrawingCacheEnabled(true);
        preview.buildDrawingCache();
        Bitmap bitmap = preview.getDrawingCache();
        progress.setVisibility(View.VISIBLE);
        FragmentManager fm = getSupportFragmentManager();
        previewCompressorFragment = (PreviewCompressorFragment) fm.findFragmentByTag(TAG_CONTAINER);
        previewCompressorFragment = new PreviewCompressorFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BITMAP, bitmap);
        previewCompressorFragment.setArguments(bundle);
        fm.beginTransaction().add(previewCompressorFragment, TAG_CONTAINER).commit();
    }

    @Override
    public void onFinished(Uri uri) {
        progress.setVisibility(View.GONE);
        createIntent(uri);
    }

    public void createIntent(Uri uri) throws ActivityNotFoundException {
        uri = createUri(this, uri);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("message/rfc822");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, uri.getLastPathSegment()));
    }

    private Uri createUri(Context ctx, Uri pdfUri) {
        File path = new File(ctx.getCacheDir(), Constants.CACHE_DIR);
        File file = new File(path, pdfUri.getLastPathSegment());
        return FileProvider.getUriForFile(ctx, "org.crazycoder.instagrampopularcollage.fileprovider", file);
    }
}
