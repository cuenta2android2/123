package com.example.jorge.app1.Databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.jorge.app1.Pojo.Quotation;

@Database(entities = {Quotation.class}, version = 1)

public abstract class RoomDatabaseHelper extends RoomDatabase {
public abstract DaoQuotation dao();
    private static  RoomDatabaseHelper  roomDatabaseHelper;
    public synchronized static  RoomDatabaseHelper getInstance(Context context) {
        if ( roomDatabaseHelper == null) {
            roomDatabaseHelper = Room.databaseBuilder(context,  RoomDatabaseHelper.class, "QuotationsTable").build();
        }
        return roomDatabaseHelper;
    }

}
