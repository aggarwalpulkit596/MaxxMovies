package com.example.pulkit.finalmovie.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pulkit on 8/5/2017.
 */

public class AccountResponse {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @SerializedName("username")
    private String username;

    public AccountResponse(Integer id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }
}
