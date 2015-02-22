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
    private static final String DATABASE_NAME = "CONSTANT.db";
    private static final int SCHEMA = 1;
    final String TITLE = "title";
    final String TABLE = "CONSTANT";
    final String BIO = "bio";
    final String PICTURE = "picture";
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
    public void InsertNote(String title,String bio,String pic) {
        try{
            open();
        }
        catch (Exception e){

        }
        ContentValues newCon = new ContentValues();
        newCon.put(TITLE, title);
        newCon.put(BIO,bio);
        newCon.put(PICTURE,pic);
        long k = database.insert(TABLE,null,newCon);
        close();
    }
//    public void InsertNoteBIO(String bio) {
//        ContentValues newCon = new ContentValues();
//        newCon.put(BIO,bio);
//        try{
//            open();
//        }
//        catch (Exception e){
//
//        }
//        database.insert(TABLE, BIO, newCon);
//        close();
//    }
//    public void InsertNotePIC(String pic) {
//        ContentValues newCon = new ContentValues();
//        newCon.put(PICTURE, pic);
//        try{
//            open();
//        }
//        catch (Exception e){
//
//        }
//        database.insert(TABLE, PICTURE, newCon);
//        close();
//    }
    protected Cursor getNode() {
//        return database.query(TABLE,new String[] {"ROWID AS _id",TITLE,BIO,PICTURE},
//                                null, null, null, null, null);
        return database.query(TABLE,null,
                                null, null, null, null, null);
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
