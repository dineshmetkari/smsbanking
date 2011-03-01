package com.nimbleteam.smsbanking;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.nimbleteam.smsbanking.data.SubDbAdapter;
import com.nimbleteam.smsbanking.data.Subscription;

public class SubscriptionsList extends ListActivity {
    private SubDbAdapter db;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	// Ignore shortcuts
	setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);

	// Inform the list we provide context menus for items
	// TODO: Do we need both lines?
	getListView().setOnCreateContextMenuListener(this);
	// registerForContextMenu(getListView());

	// Connect to the DB
	db = new SubDbAdapter(this);
	db.open();

	// Fill in the DB with init data (will work in debug mode only)
	fillInDb();
	
	// Update display
	refreshList();
    }

    
    private void fillInDb() {
	if (!SmsBanking.DEBUG) return;
	
	db.clean();
	db.createSubscription("Balance", "BAL %");
	db.createSubscription("Velcom 20", "VEL 20000 %");
	db.createSubscription("Velcom ?", "VEL ? %");
    }


    private void refreshList() {
	Cursor notesCursor = db.fetchAllSubscriptions();
	startManagingCursor(notesCursor);

	// Create an array to specify the fields we want to display in the list (only TITLE)
	String[] from = new String[] { Subscription.KEY_TITLE };

	// and an array of the fields we want to bind those fields to (in this case just text1)
	int[] to = new int[] { R.id.sub_row_title };

	// Now create a simple cursor adapter and set it to display
	SimpleCursorAdapter subs = new SimpleCursorAdapter(this, R.layout.sub_row, notesCursor, from, to);
	setListAdapter(subs);
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        refreshList();
    }
}
