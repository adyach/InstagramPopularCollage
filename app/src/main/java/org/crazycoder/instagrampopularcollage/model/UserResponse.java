package org.crazycoder.instagrampopularcollage.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * UserResponse
 *
 * Created by Andrey Dyachkov on 31/05/15.
 */
public class UserResponse {

    @SerializedName("data")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }
}
