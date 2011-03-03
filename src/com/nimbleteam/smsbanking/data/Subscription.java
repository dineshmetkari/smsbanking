package com.nimbleteam.smsbanking.data;

import com.nimbleteam.android.Entity;

public interface Subscription extends Entity {
    public static final String DATABASE_TABLE = "sub";
    
    public static final String SQL_CREATE = "create table sub (" +
	    "_id integer primary key autoincrement, " + // 0
	    "title text not null, " + // 1 
	    "body text not null);"; // 2
        
    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    
    public static final int COLUMN_INDEX_TITLE = 1;
    public static final int COLUMN_INDEX_BODY = 2;
    
    public static final String[] PROJECTION_ALL = new String[] { Entity.KEY_ID, KEY_TITLE, KEY_BODY };
}
