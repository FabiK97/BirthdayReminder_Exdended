package com.example.birthdayreminder;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BirthdayDao {

    @Query("SELECT * FROM Birthday ORDER BY month ASC, day ASC")
    public List<Birthday> getAllBirthdays();

    @Query("SELECT * FROM Birthday WHERE id = :id")
    public Birthday get(int id);

    @Insert
    public void addBirthday(Birthday birthday);

    @Delete
    public void deleteBirthday(Birthday birthday);

    @Query("DELETE FROM Birthday WHERE id = :id")
    public int deleteBirthdayById(int id);

}
