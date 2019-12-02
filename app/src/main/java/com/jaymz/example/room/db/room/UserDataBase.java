package com.jaymz.example.room.db.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.jaymz.example.room.db.room.dao.UserDao;

/**
 * Created by Mazen on 2019/11/15
 * 数据库创建及迭代类
 */
@Database(entities = {User.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class UserDataBase extends RoomDatabase {
    private static UserDataBase INSTANCE;
    private static Object sLock = new Object();

    public abstract UserDao userDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static UserDataBase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, UserDataBase.class, "User.db")
                        .addMigrations(MIGRATION_1_2)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
