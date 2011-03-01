package com.nimbleteam.smsbanking.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "data";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table sub ("
	    + "_id integer primary key autoincrement, "
	    + "title text not null, " + "body text not null" + ");";

    public DatabaseOpenHelper(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
	db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	Log.w(getClass().getName(), "Upgrading database from version "
		+ oldVersion + " to " + newVersion
		+ ", which will destroy all old data");

	db.execSQL("DROP TABLE IF EXISTS sub");
	onCreate(db);
    }
}
