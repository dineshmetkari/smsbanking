package com.nimbleteam.smsbanking;

import android.os.Bundle;
import android.widget.EditText;

import com.nimbleteam.android.EditActivity;

public class PhoneEdit extends EditActivity {
    private Preferences preferences;
    
    public PhoneEdit() {
	super(R.string.edit_phone, R.layout.phone_edit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	preferences = new Preferences(this);
	
	super.onCreate(savedInstanceState);	
    }

    protected void loadData() {
	getPhoneEditText().setText(preferences.getPhoneNumber());
    }

    protected void saveData() {
	preferences.setPhoneNumber(getPhoneEditText().getText().toString());
	preferences.save();
    }
    
    private EditText getPhoneEditText() {
	return (EditText) findViewById(R.id.phone);
    }
}
