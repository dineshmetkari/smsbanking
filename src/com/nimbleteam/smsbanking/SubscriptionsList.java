package com.nimbleteam.smsbanking;

import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.nimbleteam.smsbanking.data.SubDbAdapter;
import com.nimbleteam.smsbanking.data.Subscription;

public class SubscriptionsList extends ListActivity {
    private static final String TAG = SubscriptionsList.class.getSimpleName();
    
    public static final int MENU_ITEM_EXECUTE =		Menu.FIRST;
    public static final int MENU_ITEM_EDIT = 		Menu.FIRST + 1;
    public static final int MENU_ITEM_DELETE = 		Menu.FIRST + 2;
    
    public static final int MENU_ITEM_INSERT = 		Menu.FIRST + 10;
    public static final int MENU_ITEM_EDIT_PIN = 	Menu.FIRST + 11;
    public static final int MENU_ITEM_EDIT_NUMBER = 	Menu.FIRST + 12;
    public static final int MENU_ITEM_HELP = 		Menu.FIRST + 13;
    
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
	if (!SmsBanking.DEBUG)
	    return;

	db.clean();
	db.createSubscription("Balance", "BAL %");
	db.createSubscription("Velcom 20", "VEL 20000 %");
	db.createSubscription("Velcom ?", "VEL ? %");
    }

    private void refreshList() {
	Cursor notesCursor = db.fetchAllSubscriptions();
	startManagingCursor(notesCursor);

	// Create an array to specify the fields we want to display in the list
	// (only TITLE)
	String[] from = new String[] { Subscription.KEY_TITLE };

	// and an array of the fields we want to bind those fields to (in this
	// case just text1)
	int[] to = new int[] { R.id.sub_row_title };

	// Now create a simple cursor adapter and set it to display
	SimpleCursorAdapter subs = new SimpleCursorAdapter(this,
		R.layout.sub_row, notesCursor, from, to);
	setListAdapter(subs);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
	    Intent intent) {
	super.onActivityResult(requestCode, resultCode, intent);
	refreshList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	super.onCreateOptionsMenu(menu);

	menu.add(0, MENU_ITEM_INSERT, 0, R.string.menu_add).setIcon(android.R.drawable.ic_menu_add);
	menu.add(0, MENU_ITEM_EDIT_PIN, 0, R.string.menu_edit_pin).setIcon(android.R.drawable.ic_menu_manage);	
	menu.add(0, MENU_ITEM_EDIT_NUMBER, 0, R.string.menu_edit_number).setIcon(android.R.drawable.ic_menu_send);
	menu.add(0, MENU_ITEM_HELP, 0, R.string.menu_help).setIcon(android.R.drawable.ic_menu_help);

	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	String message = "Menu '" + item.getItemId() + "' is not yet implemented";
	Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
	toast.show();

	switch (item.getItemId()) {
	case MENU_ITEM_INSERT:
	    
	    return true;
	case MENU_ITEM_EDIT_PIN:

	    return true;
	case MENU_ITEM_EDIT_NUMBER:

	    return true;
	case MENU_ITEM_HELP:

	    return true;
	}
	return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
	AdapterView.AdapterContextMenuInfo info;
	try {
	    info = (AdapterView.AdapterContextMenuInfo) menuInfo;
	} catch (ClassCastException e) {
	    Log.e(TAG, "bad menuInfo", e);
	    return;
	}

	Cursor cursor = (Cursor) getListAdapter().getItem(info.position);
	if (cursor == null) {
	    // For some reason the requested item isn't available, do nothing
	    return;
	}

	// Setup the menu header
	menu.setHeaderTitle(cursor.getString(Subscription.COLUMN_INDEX_TITLE));

	// Add a menu item to delete the note
	menu.add(0, MENU_ITEM_EXECUTE, 0, R.string.menu_execute);
	menu.add(0, MENU_ITEM_EDIT, 0, R.string.menu_edit);
	menu.add(0, MENU_ITEM_DELETE, 0, R.string.menu_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
	AdapterView.AdapterContextMenuInfo info;
	try {
	    info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
	} catch (ClassCastException e) {
	    Log.e(TAG, "bad menuInfo", e);
	    return false;
	}

	String message = "Menu '" + item.getItemId() + "' is not yet implemented";
	Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
	toast.show();
	
	switch (item.getItemId()) {
	case MENU_ITEM_EXECUTE:
	    
	    return true;
	case MENU_ITEM_EDIT:
	    
	    return true;
	case MENU_ITEM_DELETE:

	    return true;
	}
	return false;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
	String message = "Execution is not yet implemented";
	Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
	toast.show();
    }
}
