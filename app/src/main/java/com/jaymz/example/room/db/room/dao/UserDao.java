package com.jaymz.example.room.db.room.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jaymz.example.room.db.room.User;

/**
 * Created by Mazen on 2019/11/15
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user_info LIMIT 1")
    Cursor getUser();

    @Query("SELECT * FROM user_info LIMIT 1")
    User getUserInfo();

    @Query("SELECT * FROM user_info WHERE user_id >:userId")
    User getUserInfo(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertUser(User user);

    @Query("UPDATE user_info SET user_name = :userName WHERE user_id = :userId")
    void updateUserName(String userName, int userId);

    @Query("UPDATE user_info SET user_name = :userAge WHERE user_id = :userId")
    void updateUserAge(int userAge, int userId);

    @Query("DELETE FROM user_info")
    int deleteAllUsers();

    @Delete
    int deleteIdUser(User user);

}
