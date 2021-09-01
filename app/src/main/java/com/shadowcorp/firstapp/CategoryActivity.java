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

import com.shadowcorp.firstapp.Adapters.CategoryRecViewAdapter;
import com.shadowcorp.firstapp.models.Category;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    private Button foodButton;
    private Button tvButton;
    private Button gameButton;
    private Button dateButton;
    private Button boardgameButton;
    private Button sexButton;

    private RecyclerView categoryRecView;
    private ArrayList<Category> categories = new ArrayList<>();
    private CategoryRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Add back button behaviour
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Load categories
        initializeCategoryList();

        categoryRecView = findViewById(R.id.categoryRecView);

        adapter = new CategoryRecViewAdapter(this);

        adapter.setCategories(categories);

        categoryRecView.setAdapter(adapter);
        categoryRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void changeActivity(String category) {
        Intent intent = new Intent(this, ChoiceActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
    private void initializeCategoryList() {
        categories. add(new Category("food", getString("food_category_name"), "https://www.creativefabrica.com/wp-content/uploads/2018/10/Food-seamless-pattern-by-sabavector-580x406.jpg"));
        categories. add(new Category("tv", getString("tv_category_name"), "https://d1qxviojg2h5lt.cloudfront.net/images/01DT09FQC9HRGEEWVJ74XPYNVN/freepremiums570c.png"));
        categories. add(new Category("game", getString("game_category_name"), "https://banner2.cleanpng.com/20180827/gji/kisspng-game-controllers-joystick-home-game-console-access-game-joystick-icons-1-699-free-vector-icons-pa-5b8455c0088c99.826174221535399360035.jpg"));
        categories. add(new Category("date", getString("date_category_name"), "https://us.123rf.com/450wm/macrovector/macrovector1312/macrovector131200097/24164086-dating-paar-sc%C3%A8ne-liefde-bekentenis-vectorillustratie.jpg"));
        categories. add(new Category("boardgame", getString("boardgame_category_name"), "https://www.seekpng.com/png/detail/94-946778_games-clipart-png-graphic-freeuse-board-game-png.png"));
        categories. add(new Category("sex", getString("sex_category_name"), "https://w7.pngwing.com/pngs/360/80/png-transparent-graphy-kiss-lip-romance-heart-emoji-love-miscellaneous-photography.png"));
    }

    // For back button navigation
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public String getString(String abc) { // Ex. abc = "address1"

        int resID = getResources().getIdentifier(abc, "string",  getPackageName());

        return getResources().getString(resID);
    }
}