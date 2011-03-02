package com.nimbleteam.smsbanking;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PinEdit extends Activity {
    private Preferences preferences;
    
    private EditText pinText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	preferences = new Preferences(this);

	setTitle(R.string.edit_PIN);
	setContentView(R.layout.pin_edit);

	pinText = (EditText) findViewById(R.id.pin);
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
	pinText.setText(preferences.getPin());
    }

    private void saveData() {
	preferences.setPin(pinText.getText().toString());
	preferences.save();
    }
}
