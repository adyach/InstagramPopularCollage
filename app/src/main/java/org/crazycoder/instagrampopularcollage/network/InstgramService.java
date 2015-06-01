package org.crazycoder.instagrampopularcollage.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.crazycoder.instagrampopularcollage.Constants;
import org.crazycoder.instagrampopularcollage.model.RecentFeedResponse;
import org.crazycoder.instagrampopularcollage.model.UserResponse;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * InstgramService
 *
 * Created by Andrey Dyachkov on 31/05/15.
 */
public class InstgramService {

    private final Requester requester;

    private InstgramService() {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(Constants.URL_API).
                setConverter(new GsonConverter(gson)).
                setErrorHandler(new InstaErrorHandler()).
                setLogLevel(RestAdapter.LogLevel.BASIC).
                build();
        requester = restAdapter.create(Requester.class);
    }

    public static Requester getRequester() {
        return InstanceHolder.INSTAGRAM_SERVICE.requester;
    }

    private static final class InstanceHolder {
        private static final InstgramService INSTAGRAM_SERVICE = new InstgramService();
    }

    public interface Requester {

        @GET("/users/search?client_id=" + Constants.CLIENT_ID)
        public void searchUserByQuery(@Query("q") final String query, Callback<UserResponse> callback);

        @GET("/users/{user-id}/media/recent/?client_id=" + Constants.CLIENT_ID)
        public void getUserRecentFeed(@Path("user-id") final String userId, @Query("count") final int count, Callback<RecentFeedResponse> callback);
    }

    private class InstaErrorHandler implements ErrorHandler {
        @Override
        public Throwable handleError(RetrofitError cause) {
            if (cause.getKind() == RetrofitError.Kind.NETWORK) {
                return new Exception(cause);
            } else {
                return new Exception(cause);
            }
        }
    }

}
