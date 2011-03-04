package com.nimbleteam.smsbanking;

import android.os.Bundle;
import android.widget.EditText;

import com.nimbleteam.android.Dialogs;

import com.nimbleteam.android.EditActivity;
import com.nimbleteam.smsbanking.data.Preferences;

public class PhoneEdit extends EditActivity {
    private Preferences preferences;
    
    public PhoneEdit() {
	super(R.string.edit_phone, R.layout.phone_edit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	preferences = Preferences.getPreferences(this);
	
	super.onCreate(savedInstanceState);
	
	if (preferences.isFirstLaunch()
		|| preferences.getPhoneNumber().trim().length() == 0) {
	    Dialogs.showOkConfirmation(this, R.string.initial_setup, R.string.msg_define_teller_number, null);
	}
    }

    protected void loadData() {
	getPhoneEditText().setText(preferences.getPhoneNumber());
    }

    protected void saveData() {
	preferences.setPhoneNumber(getPhoneEditText().getText().toString());
	preferences.save(this);
    }
    
    private EditText getPhoneEditText() {
	return (EditText) findViewById(R.id.phone);
    }
}
