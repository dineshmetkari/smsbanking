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
	Button confirmButton = (Button) findViewById(R.id.ok);

	rowId = (savedInstanceState == null) ? 
		null : (Long) savedInstanceState.getSerializable(Subscription.KEY_ROWID);
	if (rowId == null) {
	    Bundle extras = getIntent().getExtras();
	    rowId = extras != null ? extras.getLong(Subscription.KEY_ROWID) : null;
	}

	populateFields();

	confirmButton.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View view) {
		setResult(RESULT_OK);
		finish();
	    }
	});
    }

    private void populateFields() {
	if (rowId != null) {
	    Cursor note = db.fetchSubscription(rowId);
	    startManagingCursor(note);
	    titleText.setText(note.getString(note.getColumnIndexOrThrow(Subscription.KEY_TITLE)));
	    bodyText.setText(note.getString(note.getColumnIndexOrThrow(Subscription.KEY_BODY)));
	}
    }

    // LIFE CYCLE

    @Override
    protected void onResume() {
	super.onResume();
	populateFields();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
	super.onSaveInstanceState(outState);
	saveState();
	outState.putSerializable(Subscription.KEY_ROWID, rowId);
    }

    private void saveState() {
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

    @Override
    protected void onPause() {
	super.onPause();
	saveState();
    }
}
