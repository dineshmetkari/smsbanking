package com.nimbleteam.smsbanking;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nimbleteam.smsbanking.data.Subscription;
import com.nimbleteam.smsbanking.data.SubscriptionProcessor;

public class SubscriptionExecute extends Activity {
    private SubscriptionProcessor processor;
    private Preferences preferences;
    
    private EditText parameterText;
    
    private Long rowId;
    private String messageTemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	preferences = new Preferences(this);
	processor = new SubscriptionProcessor(this);
	
	setTitle(R.string.send);
	setContentView(R.layout.sub_exec);

	parameterText = (EditText) findViewById(R.id.parameter);
	Button sendButton = (Button) findViewById(R.id.send);

	rowId = (savedInstanceState == null) ? 
		null : (Long) savedInstanceState.getSerializable(Subscription.KEY_ROWID);
	if (rowId == null) {
	    Bundle extras = getIntent().getExtras();
	    rowId = extras.getLong(Subscription.KEY_ROWID);
	}
	
	loadData();
	
	if (!validateSettings()) {
	    setResult(RESULT_CANCELED);
	    finish();
	}
	
	if (!messageTemplate.contains("?")) {
	    send();
	    setResult(RESULT_OK);
	    finish();
	}

	sendButton.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View view) {
		if (validateData()) {
		    send();
		    setResult(RESULT_OK);
		    finish();
		}
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
	Cursor sub = processor.fetchSubscription(rowId);
	startManagingCursor(sub);
	messageTemplate = sub.getString(sub.getColumnIndexOrThrow(Subscription.KEY_BODY));
    }

    private boolean validateSettings() {
	final String pin = preferences.getPin();
	final String phone = preferences.getPhoneNumber();
	
	if (pin == null || pin.trim().length() == 0) {
	    showToast("PIN code is not defined");
	    return false;
	}
	
	if (phone == null || phone.trim().length() == 0) {
	    showToast("Teller phone number is not defined");
	    return false;
	}
	
	return true;
    }
    
    private boolean validateData() {
	String parameter = parameterText.getText().toString();
	if (parameter == null || parameter.trim().length() == 0) {
	    showToast("Parameter can not be empty");
	    return false;
	}
	
	return true;
    }
    
    private void send() {
	final String pin = preferences.getPin();
	final String phone = preferences.getPhoneNumber();
	
	String message = messageTemplate.replace("%", pin);
	message = message.replace("?", parameterText.getText().toString());
	
	sendMessage(message, phone);
    }
    
    private void sendMessage(String message, String phone) {
	if (SmsBanking.DEBUG) {
	    showToast(phone + " > " + message);
	} else {
	    SmsManager sm = SmsManager.getDefault();
	    sm.sendTextMessage(phone, null, message, null, null);
	    showToast("Message was sent");
	}
    }
    
    private void showToast(String message) {
	Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
	toast.setGravity(Gravity.CENTER, 0, 0);
	toast.show();
    }
}
