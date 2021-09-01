package com.shadowcorp.firstapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shadowcorp.firstapp.DataAccess.AppDatabase;
import com.shadowcorp.firstapp.DataAccess.DiceDao;
import com.shadowcorp.firstapp.DataAccess.DiceSideDao;
import com.shadowcorp.firstapp.models.Dice;
import com.shadowcorp.firstapp.models.DiceSide;

import java.util.ArrayList;
import java.util.Random;

public class DiceActivity extends AppCompatActivity {

    private String category;
    private DiceDao diceDao;
    private DiceSideDao diceSideDao;
    private Dice dice;
    private ArrayList<DiceSide> diceSides = new ArrayList<>();

    private TextView diceName;
    private Button rollButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        // Add back button behaviour
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        int diceId = getIntent().getIntExtra("diceId", 0);

        diceDao = AppDatabase.getInstance(getApplicationContext()).diceDao();
        diceSideDao = AppDatabase.getInstance(getApplicationContext()).diceSideDao();

        dice = diceDao.getById(diceId);
        diceSides = new ArrayList<>(diceSideDao.getDiceSidesForDice(diceId));

        diceName = findViewById(R.id.diceName);
        diceName.setText(dice.name);

        rollButton = findViewById(R.id.rollButton);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice();
            }
        });
    }

    private void rollDice() {
        Random rand = new Random();
        int position = rand.nextInt(diceSides.size());
        String roll = diceSides.get(position).name;

        Toast.makeText(this, String.format("You rolled %s", roll), Toast.LENGTH_LONG).show();
    }

    // For back button navigation
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}