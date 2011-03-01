package com.nimbleteam.smsbanking.data;

import com.nimbleteam.smsbanking.SmsBanking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static com.nimbleteam.smsbanking.data.Subscription.*;

public class SubDbAdapter {
    private static final String DATABASE_TABLE = "sub";

    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase db;

    private final Context ctx;

    public SubDbAdapter(Context ctx) {
	this.ctx = ctx;
    }

    /**
     * Open the database. If it cannot be opened, try to create a new instance
     * of the database. If it cannot be created, throw an exception to signal
     * the failure.
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException
     *             if the database could be neither opened or created
     */
    public SubDbAdapter open() throws SQLException {
	openHelper = new DatabaseOpenHelper(ctx);
	db = openHelper.getWritableDatabase();
	return this;
    }

    public void close() {
	openHelper.close();
    }

    /**
     * Clean up all data from the DB. Works in debug mode only. 
     * This is very 'rude' method and is a subject to be removed.
     */
    public void clean() {
	if (!SmsBanking.DEBUG) {
	    throw new RuntimeException("Database clean up can not be performed in production mode");
	}

	db.execSQL("DROP TABLE IF EXISTS sub");
	openHelper.onCreate(db);
    }
    
    /**
     * Create a new subscription using the title and body provided. If the sub
     * is successfully created return the new rowId for that sub, otherwise
     * return a -1 to indicate failure.
     * 
     * @param title
     *            the title of the sub
     * @param body
     *            the body of the sub
     * @return rowId or -1 if failed
     */
    public long createSubscription(String title, String body) {
	ContentValues initialValues = new ContentValues();
	initialValues.put(KEY_TITLE, title);
	initialValues.put(KEY_BODY, body);

	return db.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Update the subscription using the details provided. The sub to be updated
     * is specified using the rowId, and it is altered to use the title and body
     * values passed in
     * 
     * @param rowId
     *            id of sub to update
     * @param title
     *            value to set sub title to
     * @param body
     *            value to set sub body to
     * @return true if the sub was successfully updated, false otherwise
     */
    public boolean updateSubscription(long rowId, String title, String body) {
	ContentValues args = new ContentValues();
	args.put(KEY_TITLE, title);
	args.put(KEY_BODY, body);

	return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Delete the subscription with the given rowId
     * 
     * @param rowId
     *            id of sub to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteSubscription(long rowId) {
	return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all subscriptions in the database
     * 
     * @return Cursor over all notes
     */
    public Cursor fetchAllSubscriptions() {
	return db.query(DATABASE_TABLE, PROJECTION_ALL, null, null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the subscription that matches the given
     * rowId
     * 
     * @param rowId
     *            id of sub to retrieve
     * @return Cursor positioned to matching sub, if found
     * @throws SQLException
     *             if sub could not be found/retrieved
     */
    public Cursor fetchSubscription(long rowId) throws SQLException {
	Cursor cursor = db.query(true, DATABASE_TABLE, PROJECTION_ALL, KEY_ROWID + "=" + rowId,
		null, null, null, null, null);
	if (cursor != null) {
	    cursor.moveToFirst();
	}
	return cursor;
    }
}
