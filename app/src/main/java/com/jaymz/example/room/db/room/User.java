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

    @Ignore
    public User() {
    }

    private User(Builder builder) {
        if (builder.isFirst()) {
            this.mId = 1;
        } else {
            this.mId = builder.userId;
        }
        this.mUserName = builder.userName;
        this.mUserAge = builder.userAge;
        this.mDate = new Date(System.currentTimeMillis());
    }

    public User(String userName, int userAge) {
        this.mId = 1;
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

    @Override
    public String toString() {
        return "User{" +
                "mId=" + mId +
                ", mUserName='" + mUserName + '\'' +
                ", mUserAge=" + mUserAge +
                ", mDate=" + mDate +
                '}';
    }

    public static class Builder {
        private boolean first;

        private int userId;

        private String userName;

        private int userAge;

        private Date data;

        public boolean isFirst() {
            return first;
        }

        public Builder setFirst(boolean first) {
            this.first = first;
            return this;
        }

        public int getUserId() {
            return userId;
        }

        public Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public String getUserName() {
            return userName;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public int getUserAge() {
            return userAge;
        }

        public Builder setUserAge(int userAge) {
            this.userAge = userAge;
            return this;
        }

        public Date getData() {
            return data;
        }

        public Builder setData(Date data) {
            this.data = data;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
