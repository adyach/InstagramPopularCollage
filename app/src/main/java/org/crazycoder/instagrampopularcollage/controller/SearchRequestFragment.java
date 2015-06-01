package org.crazycoder.instagrampopularcollage.controller;

import android.app.AlertDialog;
import android.content.Intent;

import org.crazycoder.instagrampopularcollage.Constants;
import org.crazycoder.instagrampopularcollage.InstagramPopularCollage;
import org.crazycoder.instagrampopularcollage.R;
import org.crazycoder.instagrampopularcollage.model.User;
import org.crazycoder.instagrampopularcollage.model.UserResponse;
import org.crazycoder.instagrampopularcollage.network.InstgramService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * SearchRequestFragment
 *
 * Created by Andrey Dyachkov on 01/06/15.
 */
public class SearchRequestFragment extends RequestFragment {

    @Override
    protected void loadData() {
        String nickname = getArguments().getString(Constants.NICKNAME);
        InstgramService.getRequester().searchUserByQuery(nickname, callback);
    }
}
