package com.nimbleteam.smsbanking;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;

import com.nimbleteam.android.EntityEditActivity;
import com.nimbleteam.android.Dialogs;
import com.nimbleteam.smsbanking.data.Subscription;
import com.nimbleteam.smsbanking.data.SubscriptionProcessor;

public class SubscriptionEdit extends EntityEditActivity {
    private SubscriptionProcessor processor;
  
    public SubscriptionEdit() {
	super(R.string.edit_sub, R.layout.sub_edit);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	processor = new SubscriptionProcessor(this);
	
	super.onCreate(savedInstanceState);
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
