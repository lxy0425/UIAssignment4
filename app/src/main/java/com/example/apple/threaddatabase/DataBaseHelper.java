package com.example.apple.threaddatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.SQLException;
import java.util.ArrayList;

/**
* Created by apple on 15-01-15.
*/
public class DataBaseHelper extends SQLiteOpenHelper {
    DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                   int version){

        super(context,name,factory,version);
    }
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE CONSTANT(title TEXT,value REAL)");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onCreate(db);
        throw new RuntimeException("How did we get here?");
    }
}
