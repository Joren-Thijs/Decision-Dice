package com.shadowcorp.firstapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.shadowcorp.firstapp.Adapters.DiceRecViewAdapter;
import com.shadowcorp.firstapp.DataAccess.AppDatabase;
import com.shadowcorp.firstapp.DataAccess.DiceDao;
import com.shadowcorp.firstapp.models.Dice;

import java.util.ArrayList;

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
        // Add onclick listener to activity layout
        View activityView = getLayoutInflater().inflate(R.layout.activity_choice, null);
        activityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.closeMenu();
            }
        });
        setContentView(activityView);

        // Add back button behaviour
        ActionBar actionBar = getSupportActionBar();
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

        adapter = new DiceRecViewAdapter();

        loadDices();

        diceRecView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                adapter.closeMenu();
            }
        });

        diceRecView.setAdapter(adapter);
        diceRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadDices() {
        dices = new ArrayList<>(diceDao.getAllFromCategory(category));

        adapter.setDices(dices);
    }

    private void loadNewDiceActivity() {
        Intent intent = new Intent(this, NewDiceActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    // For back button navigation
    public boolean onOptionsItemSelected(MenuItem item) {
        if (adapter.isMenuShown()) {
            adapter.closeMenu();
        }
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (adapter.isMenuShown()) {
            adapter.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}