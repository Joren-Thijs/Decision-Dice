package com.shadowcorp.firstapp.DataAccess;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.shadowcorp.firstapp.models.DiceSide;

import java.util.List;

@Dao
public interface DiceSideDao {
    @Query("SELECT * FROM diceSide")
    List<DiceSide> getAll();

    @Query("SELECT * FROM diceSide WHERE diceId=:diceId")
    List<DiceSide> findDiceSidesForDice(final int diceId);

    @Query("SELECT * FROM diceSide WHERE id IN (:diceSideIds)")
    List<DiceSide> loadAllByIds(int[] diceSideIds);

    @Query("SELECT * FROM diceSide WHERE name LIKE :name LIMIT 1")
    DiceSide findByName(String name);

    @Insert
    void insertAll(DiceSide... dices);

    @Delete
    void delete(DiceSide dice);
}
