package com.shadowcorp.firstapp.models;

public class Category {
    public final String id;
        public String name;
        public String imageUrl;

    public Category(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
