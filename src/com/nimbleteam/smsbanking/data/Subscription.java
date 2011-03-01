package com.nimbleteam.smsbanking.data;

public interface Subscription {
    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_ROWID = "_id";
    
    public static final int COLUMN_INDEX_TITLE = 1;
    
    public static final String[] PROJECTION_ALL = new String[] { KEY_ROWID, KEY_TITLE, KEY_BODY };
}
