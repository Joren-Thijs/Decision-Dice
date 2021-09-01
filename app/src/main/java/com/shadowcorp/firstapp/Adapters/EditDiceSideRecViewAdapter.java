package com.shadowcorp.firstapp.Adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shadowcorp.firstapp.DataAccess.AppDatabase;
import com.shadowcorp.firstapp.DataAccess.DiceSideDao;
import com.shadowcorp.firstapp.R;
import com.shadowcorp.firstapp.models.DiceSide;

import java.util.ArrayList;

public class EditDiceSideRecViewAdapter extends RecyclerView.Adapter<EditDiceSideRecViewAdapter.ViewHolder> {

    ArrayList<DiceSide> diceSides = new ArrayList<>();
    private boolean isEditing;

    public EditDiceSideRecViewAdapter(boolean isEditing) {
        this.isEditing = isEditing;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_dice_side, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiceSide diceSide = diceSides.get(position);
        holder.diceSideName.setText(diceSide.name);
        holder.diceSideName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                diceSides.get(holder.getAdapterPosition()).name = holder.diceSideName.getText().toString();
            }
        });

        holder.deleteDiceSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiceSide selectedDiceSide = diceSides.get(holder.getAdapterPosition());

                if (isEditing) {
                    DiceSideDao diceSideDao = AppDatabase.getInstance(view.getContext()).diceSideDao();
                    diceSideDao.delete(selectedDiceSide);
                }
                diceSides.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return diceSides.size();
    }

    public void setDiceSides(ArrayList<DiceSide> diceSides) {
        this.diceSides = diceSides;
        notifyDataSetChanged();
    }

    public void addDiceSide(DiceSide diceSide) {
        diceSides.add(diceSide);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private EditText diceSideName;
        private ImageButton deleteDiceSide;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            diceSideName = itemView.findViewById(R.id.editDiceSideName);
            deleteDiceSide = itemView.findViewById(R.id.deleteDiceSide);
        }
    }
}
