package com.nimbleteam.smsbanking;

import android.os.Bundle;
import android.widget.EditText;

import com.nimbleteam.android.EditActivity;

public class PinEdit extends EditActivity {
    private Preferences preferences;
    
    public PinEdit() {
	super(R.string.edit_PIN, R.layout.pin_edit);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	preferences = new Preferences(this);
	
	super.onCreate(savedInstanceState);	
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
