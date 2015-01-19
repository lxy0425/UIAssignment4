package com.example.apple.threaddatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by apple on 15-01-18.
 */
// Declare Variables
public class DataBaseConnector {
    private static final String DATABASE_NAME = "constant.db";
    private static final int SCHEMA = 1;
    final String TITLE = "title";
    final String TABLE = "constant";
    private SQLiteDatabase database;
    private DataBaseHelper dbOpenHelper;

    public DataBaseConnector(Context context) {
        dbOpenHelper = new DataBaseHelper(context,DATABASE_NAME,null,SCHEMA);
    }

    // Open Database function
    public void open() throws SQLException {
        // Allow database to be in writable mode
        database = dbOpenHelper.getWritableDatabase();
    }

    // Close Database function
    public void close() {
        if (database != null)
            database.close();
    }

    // Create Database function
    public void InsertNote(String title) {
        ContentValues newCon = new ContentValues();
        newCon.put(TITLE, title);
        try{
            open();
        }
        catch (Exception e){

        }
        database.insert(TABLE, TITLE, newCon);
        close();
    }
    protected Cursor getNode() {
        return database.query(TABLE,new String[] {"ROWID AS _id",TITLE,},
                                null, null, null, null, TITLE);
    }

    public void DeleteNote() {
        try {
            open();
        }
        catch (Exception e){

        }
        database.delete(TABLE, null, null);
        close();
    }
}
