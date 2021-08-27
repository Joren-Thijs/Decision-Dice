package com.shadowcorp.firstapp.models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Dice.class,
                                  parentColumns = "id",
                                  childColumns = "diceId",
                                  onDelete = CASCADE))

public class DiceSide {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(index = true)
    public int diceId;

    public DiceSide(String name, int diceId) {
        this.name = name;
        this.diceId = diceId;
    }
}
