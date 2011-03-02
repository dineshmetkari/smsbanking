package com.nimbleteam.smsbanking;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.nimbleteam.smsbanking.data.Subscription;
import com.nimbleteam.smsbanking.data.SubscriptionProcessor;

public class SubscriptionsList extends ListActivity {
    private static final String TAG = SubscriptionsList.class.getSimpleName();

    public static final int MENU_ITEM_EXECUTE =		Menu.FIRST;
    public static final int MENU_ITEM_EDIT = 		Menu.FIRST + 1;
    public static final int MENU_ITEM_DELETE = 		Menu.FIRST + 2;
    
    private OptionsMenuDelegate optionsMenuDelegate;
    private SubscriptionProcessor processor;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	// Ignore shortcuts
	setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);

	// Inform the list we provide context menus for items
	getListView().setOnCreateContextMenuListener(this);

	// Create options menu delegate
	optionsMenuDelegate = new OptionsMenuDelegate(this);
	
	// Connect to the DB
	processor = new SubscriptionProcessor(this);

	// Update display
	refreshList();
    }
    

    private void refreshList() {
	Cursor notesCursor = processor.fetchAllSubscriptions();
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
	return optionsMenuDelegate.createOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	return optionsMenuDelegate.optionsItemSelected(item) || super.onOptionsItemSelected(item);
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
	    processor.executeSubscription(info.id);
	    return true;
	case MENU_ITEM_EDIT:
	    Intent i = new Intent(this, SubscriptionEdit.class);
	    i.putExtra(Subscription.KEY_ROWID, info.id);
	    startActivityForResult(i, 0);
	    return true;
	case MENU_ITEM_DELETE:
	    processor.deleteSubscription(info.id);
	    refreshList();
	    return true;
	}
	return false;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
	processor.executeSubscription(id);
    }
}
