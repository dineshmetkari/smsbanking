package com.nimbleteam.smsbanking;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.nimbleteam.android.Dialogs;
import com.nimbleteam.android.EntityEditActivity;
import com.nimbleteam.smsbanking.data.Preferences;
import com.nimbleteam.smsbanking.data.Subscription;
import com.nimbleteam.smsbanking.data.SubscriptionProcessor;

public class SubscriptionEdit extends EntityEditActivity {
    private static final int MENU_ITEM_QUICK_HELP = 0;
    private static final int MENU_ITEM_HELP = 1;
    
    private Preferences preferences;
    private SubscriptionProcessor processor;
  
    public SubscriptionEdit() {
	super(R.string.edit_sub, R.layout.sub_edit);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	processor = new SubscriptionProcessor(this);
	preferences = Preferences.getPreferences(this);
	
	super.onCreate(savedInstanceState);
	
	if (preferences.isFirstLaunch() || processor.fetchAllSubscriptions().getCount() == 0) {
	    Dialogs.showOkConfirmation(this, R.string.initial_setup, R.string.msg_create_sub, null);
	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	super.onCreateOptionsMenu(menu);
	menu.add(0, MENU_ITEM_QUICK_HELP, 0, R.string.menu_quick_help).setIcon(android.R.drawable.ic_menu_search);
	menu.add(0, MENU_ITEM_HELP, 0, R.string.menu_help).setIcon(android.R.drawable.ic_menu_help);
	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case MENU_ITEM_QUICK_HELP:
	    Dialogs.showOkConfirmation(this, R.string.help, R.string.msg_create_sub_help, null);
	    return true;
	case MENU_ITEM_HELP:
	    Intent browserIntent = new Intent("android.intent.action.VIEW",
			Uri.parse("http://code.google.com/p/smsbanking/wiki/Help"));
	    startActivity(browserIntent);
	    return true;
	}
	return false;
    }

    protected void loadData() {
	if (getEntityId() != null) {
	    Cursor sub = processor.fetchSubscription(getEntityId());
	    startManagingCursor(sub);
	    getTitleEditText().setText(sub.getString(Subscription.COLUMN_INDEX_TITLE));
	    getBodyEditText().setText(sub.getString(Subscription.COLUMN_INDEX_BODY));
	} else {
	    setTitle(R.string.add_sub);
	}
    }
     
    protected boolean validateData() {
	String body = getBodyEditText().getText().toString();
	if (body == null || body.trim().length() == 0) {
	    Dialogs.showToast(this, R.string.msg_empty_body);
	    return false;
	}
	
	String title = getTitleEditText().getText().toString();
	if (title == null || title.trim().length() == 0) {
	    title = body.length() >= 20 ? body.substring(0, 20) : body;
	    getTitleEditText().setText(title);
	}
	
	if (!body.contains("%")) {
	    Dialogs.showOkConfirmation(this, R.string.add_sub, R.string.msg_no_pin_placeholder, null);
	    return false;
	}
	
	return true;
    }
    
    protected void saveData() {
	String title = getTitleEditText().getText().toString();
	String body = getBodyEditText().getText().toString();

	if (getEntityId() == null) {
	    long id = processor.createSubscription(title, body);
	    if (id > 0) {
		setEntityId(id);
	    }
	} else {
	    processor.updateSubscription(getEntityId(), title, body);
	}
    }
    
    private EditText getTitleEditText() {
	return (EditText) findViewById(R.id.title);
    }
    
    private EditText getBodyEditText() {
	return (EditText) findViewById(R.id.body);
    }
}
