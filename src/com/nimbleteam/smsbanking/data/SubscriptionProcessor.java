package com.nimbleteam.smsbanking.data;

import static com.nimbleteam.smsbanking.data.Subscription.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.nimbleteam.android.Entity;

public class SubscriptionProcessor {
    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase db;

    private final Context ctx;

    public SubscriptionProcessor(Context ctx) {
	this.ctx = ctx;
	open();
    }

    private SubscriptionProcessor open() throws SQLException {
	openHelper = new DatabaseOpenHelper(ctx);
	db = openHelper.getWritableDatabase();
	return this;
    }

    public void close() {
	openHelper.close();
    }
    
    public long createSubscription(String title, String body) {
	ContentValues initialValues = new ContentValues();
	initialValues.put(KEY_TITLE, title);
	initialValues.put(KEY_BODY, body);

	return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean updateSubscription(long rowId, String title, String body) {
	ContentValues args = new ContentValues();
	args.put(KEY_TITLE, title);
	args.put(KEY_BODY, body);
	
	return db.update(DATABASE_TABLE, args, Entity.KEY_ID + "=" + rowId, null) > 0;
    }

    public boolean deleteSubscription(long rowId) {
	return db.delete(DATABASE_TABLE, Entity.KEY_ID + "=" + rowId, null) > 0;
    }

    public Cursor fetchAllSubscriptions() {
	return db.query(DATABASE_TABLE, PROJECTION_ALL, null, null, null, null, null);
    }

    public Cursor fetchSubscription(long rowId) throws SQLException {
	Cursor cursor = db.query(true, DATABASE_TABLE, PROJECTION_ALL, Entity.KEY_ID + "=" + rowId,
		null, null, null, null, null);
	
	if (cursor == null) {
	    throw new RuntimeException("Attempted to selected sub with id = " + rowId + ", which doesn't exist");
	}
	
	cursor.moveToFirst();
	
	return cursor;
    }
}
