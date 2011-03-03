package com.nimbleteam.smsbanking.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    public static final String PREFS_NAME = "NimbleSmsBankingPrefsFile";
    
    public static final String FIRST_LAUNCH = "first_launch";
    public static final String PIN = "pin";
    public static final String PHONE = "phone";
    public static final String EXECUTE_ON_TAP = "execute_on_tap";
    public static final String CONFIRM_EXECUTION = "confirm_execution";

    private boolean firstLaunch = true;
    private String pin = "";
    private String phoneNumber = "";
    private boolean executeOnTap = true;
    private boolean confirmOnExecution = true;
    
    private Activity activity;
    
    
    public Preferences(Activity activity) {
	this.activity = activity;
	load();
    }
    
    
    /**
     * Loads preferences from secured storage
     */
    public void load() {
	SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
	firstLaunch = settings.getBoolean(FIRST_LAUNCH, firstLaunch);
	pin = settings.getString(PIN, pin);
	phoneNumber = settings.getString(PHONE, phoneNumber);
	executeOnTap = settings.getBoolean(EXECUTE_ON_TAP, executeOnTap);
	confirmOnExecution = settings.getBoolean(CONFIRM_EXECUTION, confirmOnExecution);
    }
    
    /**
     * Commits current preferences state updating options' values if any are changed
     */
    public void save() {
	SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
	SharedPreferences.Editor editor = settings.edit();
	editor.putBoolean(FIRST_LAUNCH, firstLaunch);
	editor.putString(PIN, pin);
	editor.putString(PHONE, phoneNumber);
	editor.putBoolean(EXECUTE_ON_TAP, executeOnTap);
	editor.putBoolean(CONFIRM_EXECUTION, confirmOnExecution);
	editor.commit();
    }

    
    
    public boolean isFirstLaunch() {
        return firstLaunch;
    }


    public void setFirstLaunch(boolean firstLaunch) {
        this.firstLaunch = firstLaunch;
    }


    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isExecuteOnTap() {
        return executeOnTap;
    }

    public void setExecuteOnTap(boolean executeOnTap) {
        this.executeOnTap = executeOnTap;
    }

    public boolean isConfirmOnExecution() {
        return confirmOnExecution;
    }

    public void setConfirmOnExecution(boolean confirmOnExecution) {
        this.confirmOnExecution = confirmOnExecution;
    }
}
