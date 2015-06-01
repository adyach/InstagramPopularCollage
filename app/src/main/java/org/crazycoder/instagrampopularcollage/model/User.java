package org.crazycoder.instagrampopularcollage.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User
 * <p/>
 * Created by Andrey Dyachkov on 31/05/15.
 */
public class User implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("username")
    private String nickName;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("profile_picture")
    private String profilePicture;

    public String getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id='").append(id).append('\'');
        sb.append(", nickName='").append(nickName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", profilePicture='").append(profilePicture).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
