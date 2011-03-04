package com.nimbleteam.smsbanking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.nimbleteam.android.Dialogs;
import com.nimbleteam.smsbanking.data.Preferences;

public class InitialSetupActivity extends Activity {
    private static final int ACTIVITY_EDIT_PHONE = 0;
    private static final int ACTIVITY_EDIT_PIN   = 1;
    private static final int ACTIVITY_ADD_SUB    = 2;
    private static final int SETUP_COMPLETE      = 3;
    
    private Preferences preferences;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	preferences = Preferences.getPreferences(this);
	
	launchStage(ACTIVITY_EDIT_PHONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	
	launchStage(requestCode + 1);
    }
    
    private void launchStage(int stage) {
	switch (stage) {
	case ACTIVITY_EDIT_PHONE:
	    	Intent intentEditPhone = new Intent(this, PhoneEdit.class);
		startActivityForResult(intentEditPhone, ACTIVITY_EDIT_PHONE);
	    break;
	case ACTIVITY_EDIT_PIN:
	    	Intent intentEditPin = new Intent(this, PinEdit.class);
		startActivityForResult(intentEditPin, ACTIVITY_EDIT_PIN);
	    break;
	case ACTIVITY_ADD_SUB:
	    	Intent intentEditSub = new Intent(this, SubscriptionEdit.class);
		startActivityForResult(intentEditSub, ACTIVITY_ADD_SUB);
	    break;
	case SETUP_COMPLETE:
	    // all set-up activities are complete
	    preferences.setFirstLaunch(false);
	    preferences.save(this);
	    Dialogs.showToast(this, R.string.msg_initial_setup_complete);
	    finish();
	    break;
	default:
	    throw new AssertionError("Stages sequence is incorrect");
	}
    }
}
