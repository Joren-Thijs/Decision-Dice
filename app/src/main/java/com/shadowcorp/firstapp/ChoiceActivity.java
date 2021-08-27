package com.shadowcorp.firstapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.shadowcorp.firstapp.Adapters.DiceRecViewAdapter;
import com.shadowcorp.firstapp.Adapters.EditDiceSideRecViewAdapter;
import com.shadowcorp.firstapp.DataAccess.AppDatabase;
import com.shadowcorp.firstapp.DataAccess.DiceDao;
import com.shadowcorp.firstapp.models.Dice;
import com.shadowcorp.firstapp.models.DiceSide;

import java.util.ArrayList;
import java.util.List;

public class ChoiceActivity extends AppCompatActivity {

    private String category;
    private Button newDiceButton;
    private DiceDao diceDao;

    private RecyclerView diceRecView;
    private DiceRecViewAdapter adapter;
    ArrayList<Dice> dices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        // Add back button behaviour
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        category = getIntent().getStringExtra("category");

        newDiceButton = findViewById(R.id.newDiceButton);
        newDiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNewDiceActivity();
            }
        });

        diceDao = AppDatabase.getInstance(getApplicationContext()).diceDao();

        setupDiceRecycleView();
    }

    private void setupDiceRecycleView() {
        diceRecView = findViewById(R.id.diceRecView);

        dices = new ArrayList<>(diceDao.getAllFromCategory(category));

        adapter = new DiceRecViewAdapter();

        adapter.setDices(dices);

        diceRecView.setAdapter(adapter);
        diceRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadNewDiceActivity() {
        Intent intent = new Intent(this, NewDiceActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    // For back button navigation
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}