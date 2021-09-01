package com.shadowcorp.firstapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shadowcorp.firstapp.ChoiceActivity;
import com.shadowcorp.firstapp.R;
import com.shadowcorp.firstapp.models.Category;

import java.util.ArrayList;

public class CategoryRecViewAdapter extends RecyclerView.Adapter<CategoryRecViewAdapter.ViewHolder> {

    ArrayList<Category> categories = new ArrayList<>();
    private Context context;

    public CategoryRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        CategoryRecViewAdapter.ViewHolder holder = new CategoryRecViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(category.name);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChoiceActivity.class);
                intent.putExtra("category", category.id);
                view.getContext().startActivity(intent);
            }
        });

        Glide
            .with(context)
            .asBitmap()
            .load(category.imageUrl)
            .into(holder.categoryImage);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView parent;
        private ImageView categoryImage;
        private TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.category_parent);
            categoryImage = itemView.findViewById(R.id.category_image);
            categoryName = itemView.findViewById(R.id.category_name);
        }
    }
}
