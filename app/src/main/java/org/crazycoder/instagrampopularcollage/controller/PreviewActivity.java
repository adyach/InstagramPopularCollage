package org.crazycoder.instagrampopularcollage.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.crazycoder.instagrampopularcollage.Constants;
import org.crazycoder.instagrampopularcollage.R;
import org.crazycoder.instagrampopularcollage.model.User;
import org.crazycoder.instagrampopularcollage.network.InstgramService;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * PreviewActivity
 *
 * Created by Andrey Dyachkov on 31/05/15.
 */
public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
    }

    @OnClick(R.id.send)
    public void sendCollage (View view) {
        
    }
}
