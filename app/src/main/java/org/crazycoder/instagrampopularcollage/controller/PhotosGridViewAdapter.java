package org.crazycoder.instagrampopularcollage.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.crazycoder.instagrampopularcollage.R;
import org.crazycoder.instagrampopularcollage.model.Photo;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * PhotosGridViewAdapter
 * <p/>
 * Created by Andrey Dyachkov on 31/05/15.
 */
public class PhotosGridViewAdapter extends BaseAdapter {

    private final Context context;
    private final List<Photo> photos = new ArrayList<>();

    public PhotosGridViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = View.inflate(context, R.layout.item_photo, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Photo photo = getItem(position);
        holder.picked.setChecked(photo.isSelected());
        holder.picked.setVisibility(photo.isSelected() ? View.VISIBLE : View.GONE);
        holder.setCheckListener(new CheckListener(holder.picked, photo));
        Picasso.with(context)
                .load(photo.getUrl())
                .placeholder(R.mipmap.ic_load)
                .error(R.mipmap.ic_error)
                .tag(context)
                .into(new BitmapTarget(holder.photo, photo));

        return convertView;
    }

    public void addItem(Photo photo) {
        this.photos.add(photo);
    }

    public ArrayList<Photo> getSelectedPhotos() {
        ArrayList<Photo> selectedPhotos = new ArrayList<>();
        for (Photo p : photos) {
            if (p.isSelected()) {
                selectedPhotos.add(p);
            }
        }
        return selectedPhotos;
    }

    public void clear () {
        photos.clear();
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Photo getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItems(List<Photo> photos) {
        this.photos.addAll(photos);
    }

    static class ViewHolder implements View.OnClickListener {
        @InjectView(R.id.photo)
        ImageView photo;
        @InjectView(R.id.picked)
        CheckBox picked;
        private CheckListener checkListener;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            checkListener.onCheck(picked.isChecked());
        }

        public void setCheckListener(CheckListener checkListener) {
            this.checkListener = checkListener;
        }
    }

    private class BitmapTarget implements Target {

        private final ImageView imageView;
        private final Photo photo;

        public BitmapTarget(ImageView imageView, Photo photo) {
            this.imageView = imageView;
            this.photo = photo;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            photo.setBitmap(bitmap);
            imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    }

    private class CheckListener {

        private final CheckBox picked;
        private final Photo photo;

        public CheckListener(CheckBox picked, Photo photo) {
            this.picked = picked;
            this.photo = photo;
        }

        public void onCheck(boolean check) {
            photo.setSelected(!check);
            picked.setChecked(!check);
            picked.setVisibility(picked.isChecked() ? View.VISIBLE : View.GONE);
        }
    }
}
