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

//        db.execSQL("CREATE TABLE IF NOT EXISTS CONSTANT (title TEXT PRIMARY KEY,bio TEXT,picture TEXT)");
          db.execSQL("CREATE TABLE CONSTANT (title TEXT PRIMARY KEY,bio TEXT,picture TEXT,UNIQUE(title) ON CONFLICT REPLACE);");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS"+"CONSTANT");
        onCreate(db);
        throw new RuntimeException("How did we get here?");
    }
}
