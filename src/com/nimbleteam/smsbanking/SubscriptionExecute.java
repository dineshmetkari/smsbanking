package com.nimbleteam.smsbanking;

import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.EditText;

import com.nimbleteam.android.Dialogs;
import com.nimbleteam.android.EntityEditActivity;
import com.nimbleteam.smsbanking.data.Preferences;
import com.nimbleteam.smsbanking.data.Subscription;
import com.nimbleteam.smsbanking.data.SubscriptionProcessor;

public class SubscriptionExecute extends EntityEditActivity {
    private SubscriptionProcessor processor;
    private Preferences preferences;
    
    private String messageTemplate;
    
    public SubscriptionExecute() {
	super(R.string.send, R.layout.sub_exec);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	preferences = Preferences.getPreferences(this);
	processor = new SubscriptionProcessor(this);
	
	if (!validateSettings()) {
	    setResult(RESULT_CANCELED);
	    finish();
	}
	
	super.onCreate(savedInstanceState);
	
	if (!messageTemplate.contains("?")) {
	    sendMessage();
	    setResult(RESULT_OK);
	    finish();
	}
    }

    protected boolean validateSettings() {
	final String pin = preferences.getPin();
	final String phone = preferences.getPhoneNumber();
	
	if (pin == null || pin.trim().length() == 0) {
	    Dialogs.showToast(this, R.string.msg_no_pin_code);
	    return false;
	}
	
	if (phone == null || phone.trim().length() == 0) {
	    Dialogs.showToast(this, R.string.msg_no_teller_number);
	    return false;
	}
	
	return true;
    }
    
    protected void loadData() {
	Cursor sub = processor.fetchSubscription(getEntityId());
	startManagingCursor(sub);
	messageTemplate = sub.getString(Subscription.COLUMN_INDEX_BODY);
    }
    
    protected boolean validateData() {
	String parameter = getParameterEditText().getText().toString();
	if (parameter == null || parameter.trim().length() == 0) {
	    Dialogs.showToast(this, R.string.msg_no_parameter);
	    return false;
	}
	
	return true;
    }
    
    protected void saveData() {
	sendMessage();
    }
    
    private void sendMessage() {
	final String pin = preferences.getPin();
	final String phone = preferences.getPhoneNumber();	
	
	String message = messageTemplate.replace("%", pin);
	message = message.replace("?", getParameterEditText().getText().toString());

	final boolean debug = false; // enable for debug mode	
	if (debug) {
	    Dialogs.showToast(this, phone + " > " + message);
	} else {
	    SmsManager sm = SmsManager.getDefault();
	    sm.sendTextMessage(phone, null, message, null, null);
	    Dialogs.showToast(this, R.string.msg_message_sent);
	}
    }
    
    private EditText getParameterEditText() {
	return (EditText) findViewById(R.id.parameter);
    }
}
