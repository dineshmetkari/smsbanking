package com.nimbleteam.smsbanking;

import android.os.Bundle;
import android.widget.EditText;

import com.nimbleteam.android.Dialogs;
import com.nimbleteam.android.EditActivity;
import com.nimbleteam.smsbanking.data.Preferences;

public class PinEdit extends EditActivity {
    private Preferences preferences;
    
    public PinEdit() {
	super(R.string.edit_PIN, R.layout.pin_edit);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	preferences = new Preferences(this);
	
	super.onCreate(savedInstanceState);
	
	if (preferences.isFirstLaunch()
		|| preferences.getPin().trim().length() == 0) {
	    Dialogs.showOkConfirmation(this, R.string.initial_setup, R.string.msg_define_PIN, null);
	}
    }



    protected void loadData() {
	getPinEditText().setText(preferences.getPin());
    }

    protected void saveData() {
	preferences.setPin(getPinEditText().getText().toString());
	preferences.save();
    }
    
    private EditText getPinEditText() {
	return (EditText) findViewById(R.id.pin);
    }
}
