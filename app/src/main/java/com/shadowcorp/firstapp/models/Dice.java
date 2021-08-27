package com.shadowcorp.firstapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Dice {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "category")
    public String category;

    public Dice(String name, String category) {
        this.name = name;
        this.category = category;
    }
}
