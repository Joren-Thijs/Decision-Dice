package com.shadowcorp.firstapp.DataAccess;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.shadowcorp.firstapp.models.*;

@Database(entities = { Dice.class, DiceSide.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DiceDao diceDao();
    public abstract DiceSideDao diceSideDao();
}

