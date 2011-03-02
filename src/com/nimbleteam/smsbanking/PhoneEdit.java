package com.nimbleteam.smsbanking;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PhoneEdit extends Activity {
    private Preferences preferences;
    
    private EditText phoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	preferences = new Preferences(this);
	
	setTitle(R.string.edit_phone);
	setContentView(R.layout.phone_edit);

	phoneText = (EditText) findViewById(R.id.phone);
	Button saveButton = (Button) findViewById(R.id.save);

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

    private void loadData() {
	phoneText.setText(preferences.getPhoneNumber());
    }

    private void saveData() {
	preferences.setPhoneNumber(phoneText.getText().toString());
	preferences.save();
    }
}
