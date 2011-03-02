package com.nimbleteam.smsbanking;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PinEdit extends Activity {
    private static final String PREFS_NAME = "PinPrefsFile";

    private EditText pinText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

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
	SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
	String pin = settings.getString("pin", "");
	pinText.setText(pin);
    }

    private void saveData() {
	SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
	SharedPreferences.Editor editor = settings.edit();
	editor.putString("pin", pinText.getText().toString());
	editor.commit();
    }
}
