package org.crazycoder.instagrampopularcollage.model;

import android.graphics.Bitmap;
import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Photo
 * <p/>
 * Created by Andrey Dyachkov on 31/05/15.
 */
public class Photo {

    private String url;
    private Bitmap bitmap;
    private boolean selected;
    private static List<Photo> photos = new ArrayList<>();

    public Photo(String url) {
        this.url = url;
    }

    public Photo(Parcel in) {
        url = in.readString();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public String getUrl() {
        return url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public static void setPhotos(List<Photo> photos) {
        Photo.photos = photos;
    }

    public static List<Photo> getPhotos() {
        return photos;
    }
}
