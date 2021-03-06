package com.nimbleteam.smsbanking;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;

import com.nimbleteam.smsbanking.data.Preferences;

public class OptionsMenuDelegate {
    private static final int SETTING_ITEM = 3;
    private static final int CONFIRM_EXECUTION_ITEM = 0;
    private static final int EXECUTE_ON_TAP_ITEM = 1;
    
    private Activity parentActivity;
    private Preferences preferences;
    
    
    public OptionsMenuDelegate(Activity parentActivity) {
	this.parentActivity = parentActivity;
	this.preferences = Preferences.getPreferences(parentActivity);
    }

    public boolean createOptionsMenu(Menu menu) {
	MenuInflater inflater = parentActivity.getMenuInflater();
	inflater.inflate(R.menu.list, menu);
	
	MenuItem settingsItem = menu.getItem(SETTING_ITEM);
	SubMenu subMenu = settingsItem.getSubMenu();
	MenuItem confirmExecutionItem = subMenu.getItem(CONFIRM_EXECUTION_ITEM);
	confirmExecutionItem.setChecked(preferences.isConfirmOnExecution());
	MenuItem executeOnTapItem = subMenu.getItem(EXECUTE_ON_TAP_ITEM);
	executeOnTapItem.setChecked(preferences.isExecuteOnTap());
	
	return true;
    }
    
    public boolean optionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case R.id.menu_add:
	    addSubscription();
	    return true;
	case R.id.menu_edit_pin:
	    editPin();
	    return true;
	case R.id.menu_edit_number:
	    editTellerPhoneNumber();
	    return true;
	case R.id.menu_confirm_exec:
	    boolean confirmExecution = !preferences.isConfirmOnExecution();
	    item.setChecked(confirmExecution);
	    preferences.setConfirmOnExecution(confirmExecution);
	    preferences.save(parentActivity);
	    return true;
	case R.id.menu_exec_on_tap:
	    boolean executeOnTap = !preferences.isExecuteOnTap();
	    item.setChecked(executeOnTap);
	    preferences.setExecuteOnTap(executeOnTap);
	    preferences.save(parentActivity);
	    return true;
	case R.id.menu_help:
	    help();
	    return true;
	default:
	    return false;
	}
    }
    
    
    protected void addSubscription() {
	Intent intentEditSub = new Intent(parentActivity, SubscriptionEdit.class);
	parentActivity.startActivityForResult(intentEditSub, 0);
    }
    
    protected void editTellerPhoneNumber() {
	Intent intentEditPhone = new Intent(parentActivity, PhoneEdit.class);
	parentActivity.startActivity(intentEditPhone);
    }
    
    protected void editPin() {
	Intent intentEditPin = new Intent(parentActivity, PinEdit.class);
	parentActivity.startActivity(intentEditPin);
    }
    
    protected void help() {
	Intent browserIntent = new Intent("android.intent.action.VIEW",
		Uri.parse("http://code.google.com/p/smsbanking/wiki/Help"));
	parentActivity.startActivity(browserIntent);
    }
}
