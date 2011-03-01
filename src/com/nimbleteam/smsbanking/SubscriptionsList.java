package com.nimbleteam.smsbanking;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.nimbleteam.smsbanking.data.SubDbAdapter;
import com.nimbleteam.smsbanking.data.Subscription;

public class SubscriptionsList extends ListActivity {
    private static final String TAG = SubscriptionsList.class.getSimpleName();
    
    public static final int ACTIVITY_ADD  = 1;
    public static final int ACTIVITY_EDIT = 2;
    
    public static final int MENU_ITEM_EXECUTE =		Menu.FIRST;
    public static final int MENU_ITEM_EDIT = 		Menu.FIRST + 1;
    public static final int MENU_ITEM_DELETE = 		Menu.FIRST + 2;
    
    public static final int MENU_ITEM_ADD = 		Menu.FIRST + 10;
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
	getListView().setOnCreateContextMenuListener(this);

	// Connect to the DB
	db = new SubDbAdapter(this);
	db.open();

	// Update display
	refreshList();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	super.onCreateOptionsMenu(menu);

	menu.add(0, MENU_ITEM_ADD, 0, R.string.menu_add).setIcon(android.R.drawable.ic_menu_add);
	menu.add(0, MENU_ITEM_EDIT_PIN, 0, R.string.menu_edit_pin).setIcon(android.R.drawable.ic_menu_manage);	
	menu.add(0, MENU_ITEM_EDIT_NUMBER, 0, R.string.menu_edit_number).setIcon(android.R.drawable.ic_menu_send);
	menu.add(0, MENU_ITEM_HELP, 0, R.string.menu_help).setIcon(android.R.drawable.ic_menu_help);

	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case MENU_ITEM_ADD:
	    Intent i = new Intent(this, SubscriptionEdit.class);
	    startActivityForResult(i, ACTIVITY_ADD);
	    return true;
	case MENU_ITEM_EDIT_PIN:
	    showToast("Menu '" + item.getItemId() + "' is not yet implemented");
	    return true;
	case MENU_ITEM_EDIT_NUMBER:
	    showToast("Menu '" + item.getItemId() + "' is not yet implemented");
	    return true;
	case MENU_ITEM_HELP:
	    showToast("Menu '" + item.getItemId() + "' is not yet implemented");
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
	
	switch (item.getItemId()) {
	case MENU_ITEM_EXECUTE:
	    executeSubscription(info.id);
	    return true;
	case MENU_ITEM_EDIT:
	    Intent i = new Intent(this, SubscriptionEdit.class);
	    i.putExtra(Subscription.KEY_ROWID, info.id);
	    startActivityForResult(i, ACTIVITY_EDIT);
	    return true;
	case MENU_ITEM_DELETE:
	    db.deleteSubscription(info.id);
	    refreshList();
	    return true;
	}
	return false;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
	executeSubscription(id);
    }
    
    private void executeSubscription(long rowId) {
	showToast("Execution is not yet implemented (" + rowId + ")");
    }
    
    private void showToast(String message) {
	Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
	toast.setGravity(Gravity.CENTER, 0, 0);
	toast.show();
    }
}
