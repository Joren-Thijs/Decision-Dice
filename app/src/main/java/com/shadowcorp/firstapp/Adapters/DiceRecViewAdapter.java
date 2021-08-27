package com.shadowcorp.firstapp.Adapters;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shadowcorp.firstapp.DataAccess.AppDatabase;
import com.shadowcorp.firstapp.DataAccess.DiceDao;
import com.shadowcorp.firstapp.DiceActivity;
import com.shadowcorp.firstapp.R;
import com.shadowcorp.firstapp.models.Dice;

import java.util.ArrayList;

public class DiceRecViewAdapter extends RecyclerView.Adapter<DiceRecViewAdapter.ViewHolder> {

    private ArrayList<Dice> dices;

    public DiceRecViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dice_item, parent, false);
        DiceRecViewAdapter.ViewHolder holder = new DiceRecViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dice dice = dices.get(position);
        holder.diceName.setText(dice.name);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dice selectedDice = dices.get(holder.getAdapterPosition());
                Intent intent = new Intent(view.getContext(), DiceActivity.class);
                intent.putExtra("diceId", selectedDice.id);
                view.getContext().startActivity(intent);
            }
        });

        holder.deleteDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dice selectedDice = dices.get(holder.getAdapterPosition());

                DiceDao diceDao = AppDatabase.getInstance(view.getContext()).diceDao();

                diceDao.delete(selectedDice);

                dices.remove(holder.getAdapterPosition());

                Toast.makeText(view.getContext(), String.format("Dice %s deleted", selectedDice.name), Toast.LENGTH_SHORT).show();

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dices.size();
    }

    public void setDices(ArrayList<Dice> dices) {
        this.dices = dices;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        private LinearLayout parent;
        private TextView diceName;
        private ImageButton deleteDice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.dice_item_parent);
            diceName = itemView.findViewById(R.id.item_dice_name);
            deleteDice = itemView.findViewById(R.id.deleteDice);
        }
    }
}
