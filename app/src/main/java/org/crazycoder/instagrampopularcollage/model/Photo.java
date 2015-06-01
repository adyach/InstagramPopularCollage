package org.crazycoder.instagrampopularcollage.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Photo
 *
 * Created by Andrey Dyachkov on 31/05/15.
 */
public class Photo implements Parcelable {

    public static final Parcelable.Creator<Photo> CREATOR = new Creator<Photo>() {

        @Override
        public Photo[] newArray(int size) {

            return new Photo[size];
        }

        @Override
        public Photo createFromParcel(Parcel source) {

            return new Photo(source);
        }
    };

    private String url;
    private Bitmap bitmap;
    private boolean selected;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeParcelable(bitmap, 0);
    }
}
