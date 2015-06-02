package org.crazycoder.instagrampopularcollage.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.crazycoder.instagrampopularcollage.Constants;
import org.crazycoder.instagrampopularcollage.R;
import org.crazycoder.instagrampopularcollage.model.User;
import org.crazycoder.instagrampopularcollage.model.UserResponse;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * StartActivity
 * <p/>
 * Created by Andrey Dyachkov on 31/05/15.
 */
public class StartActivity
        extends AppCompatActivity
        implements Callback<UserResponse> {

    private static final String TAG_CONTAINER = "SearchRequestFragment";
    private SearchRequestFragment searchRequestFragment;
    @InjectView(R.id.nickname)
    EditText nickName;
    @InjectView(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.inject(this);
        FragmentManager fm = getSupportFragmentManager();
        searchRequestFragment = (SearchRequestFragment) fm.findFragmentByTag(TAG_CONTAINER);
        if (searchRequestFragment == null) {
            progress.setVisibility(View.GONE);
        } else {
            progress.setVisibility(searchRequestFragment.isLoaded() ? View.GONE : View.VISIBLE);
        }
    }

    @OnClick(R.id.show_collage)
    public void showCollage(View view) {
        progress.setVisibility(View.VISIBLE);
        FragmentManager fm = getSupportFragmentManager();
        searchRequestFragment = (SearchRequestFragment) fm.findFragmentByTag(TAG_CONTAINER);
        searchRequestFragment = new SearchRequestFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NICKNAME, nickName.getText().toString());
        searchRequestFragment.setArguments(bundle);
        fm.beginTransaction().add(searchRequestFragment, TAG_CONTAINER).commit();
    }

    @Override
    public void success(UserResponse userResponse, Response response) {
        searchRequestFragment.setLoaded(true);
        progress.setVisibility(View.GONE);
        String nickNameStr = nickName.getText().toString();
        for (User u : userResponse.getUsers()) {
            if (u.getNickName().equals(nickNameStr)) {
                Intent intent = new Intent(this, PhotosActivity.class);
                intent.putExtra(Constants.MODEL, u);
                startActivity(intent);
                return;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.error).setMessage(R.string.no_user_found).show();
    }

    @Override
    public void failure(RetrofitError error) {
        searchRequestFragment.setLoaded(true);
        progress.setVisibility(View.GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.error).setMessage(error.getMessage()).show();
    }

}
