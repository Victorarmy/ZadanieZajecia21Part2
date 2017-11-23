package com.example.model;

public enum Option {
    DODAJ("add"),
    WYSWIETL("view");


    private String url;

    Option(String name) {
        this.url = name;
    }

    public String getUrl() {
        return url;
    }
}
