package com.nimbleteam.smsbanking;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PhoneEdit extends Activity {
    private EditText phoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

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
	SharedPreferences settings = getSharedPreferences(SmsBanking.PREFS_NAME, MODE_PRIVATE);
	String phone = settings.getString("phone", "");
	phoneText.setText(phone);
    }

    private void saveData() {
	SharedPreferences settings = getSharedPreferences(SmsBanking.PREFS_NAME, MODE_PRIVATE);
	SharedPreferences.Editor editor = settings.edit();
	editor.putString("phone", phoneText.getText().toString());
	editor.commit();
    }
}
