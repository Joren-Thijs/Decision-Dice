package com.shadowcorp.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.shadowcorp.firstapp.DataAccess.AppDatabase;
import com.shadowcorp.firstapp.DataAccess.DiceDao;
import com.shadowcorp.firstapp.models.Dice;

import java.util.List;

public class ChoiceActivity extends AppCompatActivity {

    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        category = getIntent().getStringExtra("category");

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "AppDatabase").allowMainThreadQueries().build();

        DiceDao diceDao = db.diceDao();

        diceDao.insertAll(new Dice("Italian", category));

        List<Dice> dices = diceDao.getAllFromCategory(category);

        int count = dices.size();
    }
}