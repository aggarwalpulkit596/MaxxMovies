package com.example.pulkit.finalmovie.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Pulkit on 8/3/2017.
 */

public class CreditResponse {
    @SerializedName("id")
    private int id_credit;
    @SerializedName("results")
    private List<Credit> results;

    public int getId_credit() {
        return id_credit;
    }

    public void setId_credit(int id_credit) {
        this.id_credit = id_credit;
    }

    public List<Credit> getResults() {
        return results;
    }

    public void setResults(List<Credit> results) {
        this.results = results;
    }

    public List<Credit> getCredit() {
        return results;
    }

    public void setCredit(List<Credit> results) {
        this.results = results;
    }
}

