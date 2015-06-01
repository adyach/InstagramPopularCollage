package org.crazycoder.instagrampopularcollage.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * RecentFeedResponse
 *
 * Created by Andrey Dyachkov on 31/05/15.
 */
public class RecentFeedResponse {

    @SerializedName("pagination")
    private Pagination pagination;
    @SerializedName("data")
    private List<PostItem> postItems;

    public Pagination getPagination() {
        return pagination;
    }

    public List<PostItem> getPostItems() {
        return postItems;
    }

    public static class Pagination {

        @SerializedName("next_url")
        private String nextUrl;
        @SerializedName("next_max_id")
        private String nextMaxId;

        public String getNextUrl() {
            return nextUrl;
        }

        public String getNextMaxId() {
            return nextMaxId;
        }
    }

    public static class PostItem implements Comparable<PostItem> {

        @SerializedName("id")
        private String id;
        @SerializedName("type")
        private String type;
        @SerializedName("likes")
        private Likes likes;
        @SerializedName("images")
        private PostImages postImages;

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public Likes getLikes() {
            return likes;
        }

        public PostImages getPostImages() {
            return postImages;
        }

        @Override
        public int compareTo(PostItem another) {
            if (likes.getCount() > another.getLikes().getCount()) {
                return 1;
            }

            if (likes.getCount() < another.getLikes().getCount()) {
                return -1;
            }

            return 0;
        }
    }

    public static class Likes {

        @SerializedName("count")
        private int count;

        public int getCount() {
            return count;
        }
    }

    public static class PostImages {

        @SerializedName("low_resolution")
        private PostImage lowResolutionImage;
        @SerializedName("thumbnail")
        private PostImage thumbnailImage;
        @SerializedName("standard_resolution")
        private PostImage standardResolutionImage;

        public PostImage getLowResolutionImage() {
            return lowResolutionImage;
        }

        public PostImage getThumbnailImage() {
            return thumbnailImage;
        }

        public PostImage getStandardResolutionImage() {
            return standardResolutionImage;
        }

    }

    public static class PostImage {

        @SerializedName("url")
        private String url;
        @SerializedName("width")
        private int width;
        @SerializedName("height")
        private int height;

        public String getUrl() {
            return url;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

    }
}
