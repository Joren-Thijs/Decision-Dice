package com.shadowcorp.firstapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.shadowcorp.firstapp.Adapters.DiceRecViewAdapter;
import com.shadowcorp.firstapp.Adapters.DiceSideAdapter;
import com.shadowcorp.firstapp.DataAccess.AppDatabase;
import com.shadowcorp.firstapp.DataAccess.DiceDao;
import com.shadowcorp.firstapp.DataAccess.DiceSideDao;
import com.shadowcorp.firstapp.models.Dice;
import com.shadowcorp.firstapp.models.DiceSide;

import java.util.ArrayList;
import java.util.Random;

public class DiceActivity extends AppCompatActivity {

    private DiceDao diceDao;
    private DiceSideDao diceSideDao;
    private Dice dice;
    private ArrayList<DiceSide> diceSides = new ArrayList<>();

    private TextView diceName;
    private RecyclerView diceSideRecView;
    private DiceSideAdapter adapter;

    private Button rollButton;

    private PopupWindow popupWindow;
    private LinearLayout popupLayout;
    private TextView popupTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add onclick listener to activity layout
        View activityView = getLayoutInflater().inflate(R.layout.activity_dice, null);
        activityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        setContentView(activityView);

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

        diceSideRecView = findViewById(R.id.dice_side_recview);

        adapter = new DiceSideAdapter();

        adapter.setDiceSides(diceSides);

        diceSideRecView.setAdapter(adapter);
        diceSideRecView.setLayoutManager(new LinearLayoutManager(this));

        rollButton = findViewById(R.id.rollButton);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice();
            }
        });

        createPopup();
    }

    private void createPopup() {
        // Create popup
        popupWindow = new PopupWindow(this);
        // parent
        popupLayout = new LinearLayout(this);
        // card
        CardView cardView = new CardView(this);
        // inside of card
        LinearLayout cardLayout = new LinearLayout(this);
        // image
        ImageView imageView = new ImageView(this);
        //text
        popupTextView = new TextView(this);

        // linear params
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // Card params
        CardView.LayoutParams cardParams = new CardView.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        // Set orientation
        popupLayout.setOrientation(LinearLayout.VERTICAL);
        cardLayout.setOrientation(LinearLayout.VERTICAL);
        cardLayout.setPadding(0, 0, 0, 50);

        // set card properties
        cardView.setCardElevation(5);
        cardView.setRadius(50);

        // add image to cardlayout
        imageView.setLayoutParams(new FrameLayout.LayoutParams(650, 650));
        imageView.setImageResource(R.mipmap.ic_launcher_foreground);
        cardLayout.addView(imageView);

        // add text to cardlayout
        popupTextView.setText("You rolled option");
        popupTextView.setTextSize(20);
        popupTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        cardLayout.addView(popupTextView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        // add cardlayout to card
        cardView.addView(cardLayout, cardParams);

        // add card to linear layout
        popupLayout.addView(cardView, linearParams);

        // add linear layout to popup
        popupWindow.setBackgroundDrawable(new ColorDrawable( android.graphics.Color.TRANSPARENT));
        popupWindow.setContentView(popupLayout);
    }

    private void rollDice() {
        Random rand = new Random();
        int position = rand.nextInt(diceSides.size());
        String roll = diceSides.get(position).name;

        String popupText = getString(R.string.dice_rolled) + " " + roll;
        popupTextView.setText(popupText);
        popupWindow.showAtLocation(popupLayout, Gravity.CENTER, 10, 10);

        new Handler().postDelayed(new Runnable(){
            public void run() {
                popupWindow.dismiss();
            }
        }, 3 * 1000);
    }

    // For back button navigation
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}