package com.nimbleteam.smsbanking;

import com.nimbleteam.smsbanking.data.SubDbAdapter;
import com.nimbleteam.smsbanking.data.Subscription;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SubscriptionEdit extends Activity {
    private SubDbAdapter db;

    private EditText titleText;
    private EditText bodyText;
    private Long rowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	db = new SubDbAdapter(this);
	db.open();

	setTitle(R.string.edit_sub);
	setContentView(R.layout.sub_edit);
	titleText = (EditText) findViewById(R.id.title);
	bodyText = (EditText) findViewById(R.id.body);
	Button saveButton = (Button) findViewById(R.id.save);

	rowId = (savedInstanceState == null) ? 
		null : (Long) savedInstanceState.getSerializable(Subscription.KEY_ROWID);
	if (rowId == null) {
	    Bundle extras = getIntent().getExtras();
	    rowId = extras != null ? extras.getLong(Subscription.KEY_ROWID) : null;
	}

	loadData();

	saveButton.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View view) {
		saveData();
		setResult(RESULT_OK);
		finish();
	    }
	});
    }

    @Override
    protected void onResume() {
	super.onResume();
	loadData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
	super.onSaveInstanceState(outState);
	outState.putSerializable(Subscription.KEY_ROWID, rowId);
    }
    
    private void loadData() {
	if (rowId != null) {
	    Cursor note = db.fetchSubscription(rowId);
	    startManagingCursor(note);
	    titleText.setText(note.getString(note.getColumnIndexOrThrow(Subscription.KEY_TITLE)));
	    bodyText.setText(note.getString(note.getColumnIndexOrThrow(Subscription.KEY_BODY)));
	}
    }
     
    private void saveData() {
	String title = titleText.getText().toString();
	String body = bodyText.getText().toString();

	if (rowId == null) {
	    long id = db.createSubscription(title, body);
	    if (id > 0) {
		rowId = id;
	    }
	} else {
	    db.updateSubscription(rowId, title, body);
	}
    }
}
