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
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.shadowcorp.firstapp.Adapters.EditDiceSideRecViewAdapter;
import com.shadowcorp.firstapp.DataAccess.AppDatabase;
import com.shadowcorp.firstapp.DataAccess.DiceDao;
import com.shadowcorp.firstapp.DataAccess.DiceSideDao;
import com.shadowcorp.firstapp.models.Dice;
import com.shadowcorp.firstapp.models.DiceSide;

import java.time.Duration;
import java.util.ArrayList;

public class NewDiceActivity extends AppCompatActivity {

    private String category;
    private EditText editDiceName;
    private RecyclerView editDiceSideRecView;
    private EditDiceSideRecViewAdapter adapter;
    ArrayList<DiceSide> diceSides;
    private Button addOptionButton;
    private Button saveDiceButton;
    private DiceDao diceDao;
    private DiceSideDao diceSideDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dice);

        // Add back button behaviour
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        category = getIntent().getStringExtra("category");

        editDiceName = findViewById(R.id.editDiceName);

        setupDiceSideRecycleView();

        addOptionButton = findViewById(R.id.addOptionButton);
        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addDiceSide(new DiceSide(null, 0));
            }
        });

        saveDiceButton = findViewById(R.id.saveDiceButton);
        saveDiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDice();
            }
        });

        diceDao = AppDatabase.getInstance(getApplicationContext()).diceDao();
        diceSideDao = AppDatabase.getInstance(getApplicationContext()).diceSideDao();
    }

    private void setupDiceSideRecycleView() {
        editDiceSideRecView = findViewById(R.id.editDiceSideRecView);

        diceSides = new ArrayList<>();
        diceSides.add(new DiceSide(null, 0));
        diceSides.add(new DiceSide(null, 0));
        diceSides.add(new DiceSide(null, 0));

        adapter = new EditDiceSideRecViewAdapter();

        adapter.setDiceSides(diceSides);

        editDiceSideRecView.setAdapter(adapter);
        editDiceSideRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void saveDice() {

        // Validate dice
        if (!validateDice()) {
            return;
        }

        // Create new Dice
        Dice dice = new Dice(editDiceName.getText().toString(), category);

        // check if dice name already exists in db
        Dice existingDice = diceDao.findByNameInCategory(dice.name, category);
        if (existingDice != null) {
            Toast.makeText(this, String.format("Dice with name %s already exists", dice.name), Toast.LENGTH_SHORT).show();
            return;
        }

        // Add dice to db
        int diceId = (int)diceDao.insert(dice);

        // Add dice id to dice sides
        for (DiceSide diceSide : diceSides) {
            diceSide.diceId = diceId;
        }

        // add dice sides to db
        for (DiceSide diceSide : diceSides) {
            diceSideDao.insert(diceSide);
        }

        // goto dice roll activity
        Toast.makeText(this, String.format("Dice with name %s added successfully", dice.name), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DiceActivity.class);
        intent.putExtra("diceId", diceId);
        startActivity(intent);
    }

    private boolean validateDice() {

        // Check dice name is not null or empty
        String diceName = editDiceName.getText().toString();
        if(Strings.isNullOrEmpty(diceName)) {
            Toast.makeText(this, "Dice must have a name", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check dice sides or not null or empty
        for (DiceSide diceSide : diceSides) {
            if(Strings.isNullOrEmpty(diceSide.name)) {
                Toast.makeText(this, "All dice sides must have a name", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    // For back button navigation
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}