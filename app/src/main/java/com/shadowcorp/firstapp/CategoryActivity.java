package com.shadowcorp.firstapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity {

    private Button foodButton;
    private Button tvButton;
    private Button gameButton;
    private Button dateButton;
    private Button boardgameButton;
    private Button sexButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Add back button behaviour
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        foodButton = findViewById(R.id.button_food);
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(getResources().getString(R.string.category_food));
            }
        });

        tvButton = findViewById(R.id.button_tv);
        tvButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(getResources().getString(R.string.category_tv));
            }
        }));

        gameButton = findViewById(R.id.button_game);
        gameButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(getResources().getString(R.string.category_game));
            }
        }));

        dateButton = findViewById(R.id.button_date);
        dateButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(getResources().getString(R.string.category_date));
            }
        }));

        boardgameButton = findViewById(R.id.button_boardgame);
        boardgameButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(getResources().getString(R.string.category_boardgame));
            }
        }));

        sexButton = findViewById(R.id.button_sex);
        sexButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(getResources().getString(R.string.category_sex));
            }
        }));
    }

    private void changeActivity(String category) {
        Intent intent = new Intent(this, ChoiceActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    // For back button navigation
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}