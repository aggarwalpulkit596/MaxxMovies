package com.example.pulkit.finalmovie.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pulkit on 8/5/2017.
 */

public class PostResponse {
    @SerializedName("media_type")
    private String mediaType;
    @SerializedName("media_id")
    private Integer mediaId;
    @SerializedName("watchlist")
    private Boolean watchlist;

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    public Boolean getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(Boolean watchlist) {
        this.watchlist = watchlist;
    }

}
