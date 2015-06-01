package org.crazycoder.instagrampopularcollage.controller;

import org.crazycoder.instagrampopularcollage.Constants;
import org.crazycoder.instagrampopularcollage.model.Photo;
import org.crazycoder.instagrampopularcollage.model.User;
import org.crazycoder.instagrampopularcollage.network.InstgramService;

import java.util.ArrayList;
import java.util.List;

/**
 * PhotosRequestFragment
 * <p/>
 * Created by Andrey Dyachkov on 01/06/15.
 */
public class PhotosRequestFragment extends RequestFragment {

    private static final int PHOTOS_COUNT = 100;
    private List<Photo> photos;

    @Override
    protected void loadData() {
        photos = new ArrayList<>();
        User user = (User) getArguments().getSerializable(Constants.MODEL);
        InstgramService.getRequester().getUserRecentFeed(user.getId(), PHOTOS_COUNT, callback);
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }
}
