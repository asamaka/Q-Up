package com.example.android.q_up.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.q_up.data.QueueContract.QueueEntry;

/**
 * Created by asser on 7/21/16.
 */
public class QueueDbHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "queue.db";

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public QueueDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold data.
        final String SQL_CREATE_QUEUE_TABLE = "CREATE TABLE " + QueueEntry.TABLE_NAME + " (" +
                QueueEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                QueueEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                QueueEntry.COLUMN_PARTY + " INTEGER NOT NULL); ";

        sqLiteDatabase.execSQL(SQL_CREATE_QUEUE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + QueueEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public Cursor getAllNames() {
        SQLiteDatabase db =  getReadableDatabase();
        Cursor cr = db.query(
                QueueEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        if(db!=null)
            db.close();
        return cr;
    }

    public long addNewPerson(String name, int party) {
        final SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QueueEntry.COLUMN_NAME, name);
        cv.put(QueueEntry.COLUMN_PARTY, party);
        long _id = db.insert(QueueEntry.TABLE_NAME, null, cv);
        if(db!=null)
            db.close();
        return _id;
    }

    public boolean removePerson(long id) {
        final SQLiteDatabase db = getWritableDatabase();
        int result =  db.delete(QueueEntry.TABLE_NAME, QueueEntry._ID + "=" + id, null);
        if(db!=null)
            db.close();
        return result>0;
    }
}
