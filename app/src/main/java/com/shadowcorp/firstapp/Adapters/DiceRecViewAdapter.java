package com.shadowcorp.firstapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shadowcorp.firstapp.DataAccess.AppDatabase;
import com.shadowcorp.firstapp.DataAccess.DiceDao;
import com.shadowcorp.firstapp.DiceActivity;
import com.shadowcorp.firstapp.NewDiceActivity;
import com.shadowcorp.firstapp.R;
import com.shadowcorp.firstapp.models.Dice;

import java.util.ArrayList;

public class DiceRecViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Dice> dices;

    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    @Override
    public int getItemViewType(int position) {
        if (dices.get(position).isShowMenu()) {
            return SHOW_MENU;
        } else {
            return HIDE_MENU;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == SHOW_MENU) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_menu, parent, false);
            return new MenuViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dice, parent, false);
            return new DiceViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof DiceViewHolder) {
            Dice dice = dices.get(position);
            ((DiceViewHolder) holder).diceName.setText(dice.name);

            ((DiceViewHolder) holder).parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Close menu if another item has its menu open
                    if (isMenuShown()) {
                        closeMenu();
                        return;
                    }

                    // Work around for samsung bug https://stackoverflow.com/questions/31759171/recyclerview-and-java-lang-indexoutofboundsexception-inconsistency-detected-in
                    int pos = holder.getAdapterPosition();
                    if (pos < 0) {
                        pos = position;
                    }

                    Dice selectedDice = dices.get(pos);
                    Intent intent = new Intent(view.getContext(), DiceActivity.class);
                    intent.putExtra("diceId", selectedDice.id);
                    view.getContext().startActivity(intent);
                }
            });

            ((DiceViewHolder) holder).parent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Work around for samsung bug https://stackoverflow.com/questions/31759171/recyclerview-and-java-lang-indexoutofboundsexception-inconsistency-detected-in
                    int pos = holder.getAdapterPosition();
                    if (pos < 0) {
                        pos = position;
                    }

                    showMenu(pos);
                    return true;
                }
            });
        }

        if (holder instanceof MenuViewHolder) {
            ((MenuViewHolder) holder).parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closeMenu();
                }
            });

            ((MenuViewHolder) holder).editDice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Work around for samsung bug https://stackoverflow.com/questions/31759171/recyclerview-and-java-lang-indexoutofboundsexception-inconsistency-detected-in
                    int pos = holder.getAdapterPosition();
                    if (pos < 0) {
                        pos = position;
                    }

                    Dice selectedDice = dices.get(pos);
                    Intent intent = new Intent(view.getContext(), NewDiceActivity.class);
                    intent.putExtra("diceId", selectedDice.id);
                    view.getContext().startActivity(intent);
                }
            });

            ((MenuViewHolder) holder).deleteDice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Work around for samsung bug https://stackoverflow.com/questions/31759171/recyclerview-and-java-lang-indexoutofboundsexception-inconsistency-detected-in
                    int pos = holder.getAdapterPosition();
                    if (pos < 0) {
                        pos = position;
                    }

                    Dice selectedDice = dices.get(pos);
                    DiceDao diceDao = AppDatabase.getInstance(view.getContext()).diceDao();
                    diceDao.delete(selectedDice);
                    dices.remove(holder.getAdapterPosition());
                    Toast.makeText(view.getContext(), String.format("Dice %s deleted", selectedDice.name), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dices.size();
    }

    public void setDices(ArrayList<Dice> dices) {
        this.dices = dices;
    }

    public void showMenu(int position) {
        for(int i=0; i<dices.size(); i++){
            dices.get(i).setShowMenu(false);
        }
        dices.get(position).setShowMenu(true);
        notifyDataSetChanged();
    }


    public boolean isMenuShown() {
        for(int i=0; i<dices.size(); i++){
            if(dices.get(i).isShowMenu()){
                return true;
            }
        }
        return false;
    }

    public void closeMenu() {
        for(int i=0; i<dices.size(); i++){
            dices.get(i).setShowMenu(false);
        }
        notifyDataSetChanged();
    }

    public class DiceViewHolder extends RecyclerView.ViewHolder  {

        private LinearLayout parent;
        private TextView diceName;

        public DiceViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.dice_item_parent);
            diceName = itemView.findViewById(R.id.item_dice_name);
        }
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder  {

        private LinearLayout parent;
        private ImageButton editDice;
        private ImageButton deleteDice;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.edit_menu_parent);
            editDice = itemView.findViewById(R.id.edit_button);
            deleteDice = itemView.findViewById(R.id.delete_button);
        }
    }
}
