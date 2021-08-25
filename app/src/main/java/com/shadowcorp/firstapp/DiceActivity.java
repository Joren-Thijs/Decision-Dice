package com.shadowcorp.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.shadowcorp.firstapp.DataAccess.AppDatabase;
import com.shadowcorp.firstapp.DataAccess.DiceDao;
import com.shadowcorp.firstapp.models.Dice;

import java.util.List;

public class DiceActivity extends AppCompatActivity {

    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
    }
}