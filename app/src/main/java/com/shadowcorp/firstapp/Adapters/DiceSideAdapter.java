package com.shadowcorp.firstapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shadowcorp.firstapp.R;
import com.shadowcorp.firstapp.models.DiceSide;

import java.util.ArrayList;

public class DiceSideAdapter extends RecyclerView.Adapter<DiceSideAdapter.ViewHolder> {

    ArrayList<DiceSide> diceSides = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dice_side, parent, false);
        DiceSideAdapter.ViewHolder holder = new DiceSideAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiceSide diceSide = diceSides.get(position);
        holder.diceSideName.setText(diceSide.name);
    }

    @Override
    public int getItemCount() {
        return diceSides.size();
    }

    public void setDiceSides(ArrayList<DiceSide> diceSides) {
        this.diceSides = diceSides;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout parent;
        private TextView diceSideName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.dice_side_item_parent);
            diceSideName = itemView.findViewById(R.id.item_dice_side_name);
        }
    }
}
