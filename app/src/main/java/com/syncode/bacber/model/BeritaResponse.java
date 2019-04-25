package com.syncode.bacber.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeritaResponse {

    @SerializedName("articles")
    List<Berita> beritaList;


    public BeritaResponse(List<Berita> beritaList) {
        this.beritaList = beritaList;
    }

    public List<Berita> getBeritaList() {
        return beritaList;
    }
}
