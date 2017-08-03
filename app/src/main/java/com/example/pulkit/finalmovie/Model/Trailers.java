package com.example.pulkit.finalmovie.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pulkit on 8/2/2017.
 */

public class Trailers {
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trailers(String key, String name) {
        this.key = key;
        this.name = name;
    }

}
