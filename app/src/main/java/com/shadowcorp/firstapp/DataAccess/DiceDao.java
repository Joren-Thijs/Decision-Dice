package com.shadowcorp.firstapp.DataAccess;

import androidx.room.*;

import com.shadowcorp.firstapp.models.Dice;

import java.util.List;

@Dao
public interface DiceDao {
    @Query("SELECT * FROM dice")
    List<Dice> getAll();

    @Query("SELECT * FROM dice WHERE id LIKE :id")
    Dice getById(int id);

    @Query("SELECT * FROM dice WHERE category LIKE :category")
    List<Dice> getAllFromCategory(String category);

    @Query("SELECT * FROM dice WHERE id IN (:diceIds)")
    List<Dice> loadAllByIds(int[] diceIds);

    @Query("SELECT * FROM dice WHERE name LIKE :name LIMIT 1")
    Dice findByName(String name);

    @Query("SELECT * FROM dice WHERE name LIKE :name AND category LIKE :category LIMIT 1")
    Dice findByNameInCategory(String name, String category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Dice... dices);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Dice dice);

    @Delete
    void delete(Dice dice);
}
