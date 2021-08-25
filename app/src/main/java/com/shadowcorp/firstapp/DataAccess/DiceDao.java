package com.shadowcorp.firstapp.DataAccess;

import androidx.room.*;

import com.shadowcorp.firstapp.models.Dice;

import java.util.List;

@Dao
public interface DiceDao {
    @Query("SELECT * FROM dice")
    List<Dice> getAll();

    @Query("SELECT * FROM dice WHERE category LIKE :category")
    List<Dice> getAllFromCategory(String category);

    @Query("SELECT * FROM dice WHERE id IN (:diceIds)")
    List<Dice> loadAllByIds(int[] diceIds);

    @Query("SELECT * FROM dice WHERE name LIKE :name LIMIT 1")
    Dice findByName(String name);

    @Insert
    void insertAll(Dice... dices);

    @Delete
    void delete(Dice dice);
}
