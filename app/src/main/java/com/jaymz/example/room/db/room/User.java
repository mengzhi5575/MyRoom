package com.jaymz.example.room.db.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Random;

/**
 * Created by Mazen on 2019/11/15
 */
@Entity(tableName = "user_info")
public class User {
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    private int mId;

    @ColumnInfo(name = "user_name")
    private String mUserName;

    @ColumnInfo(name = "user_age")
    private int mUserAge;

    @ColumnInfo(name = "last_update")
    private Date mDate;

    public User(String userName, int userAge) {
        this.mId = new Random(Integer.MAX_VALUE).nextInt();
        this.mUserName = userName;
        this.mUserAge = userAge;
        this.mDate = new Date(System.currentTimeMillis());
    }

    @Ignore
    public User(int id, String userName, int userAge) {
        this.mId = id;
        this.mUserName = userName;
        this.mUserAge = userAge;
        this.mUserAge = userAge;
        this.mDate = new Date(System.currentTimeMillis());
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public int getUserAge() {
        return mUserAge;
    }

    public void setUserAge(int userAge) {
        this.mUserAge = userAge;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }
}
