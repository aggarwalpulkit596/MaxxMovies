package com.example.pulkit.finalmovie.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pulkit on 8/3/2017.
 */

public class Credit {
    @SerializedName("character")
    private String character;
    @SerializedName("name")
    private String name;
    @SerializedName("profile_path")
    private String profilepath;

    public Credit(String character, String name, String profilepath) {
        this.character = character;
        this.name = name;
        this.profilepath = profilepath;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilepath() {
        return "http://image.tmdb.org/t/p/w500" + profilepath;
    }

    public void setProfilepath(String profilepath) {
        this.profilepath = profilepath;
    }

}
