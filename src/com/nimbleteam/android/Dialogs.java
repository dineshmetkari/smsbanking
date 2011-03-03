package com.nimbleteam.android;

import com.nimbleteam.smsbanking.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.Toast;

public class Dialogs {
    private Dialogs() {};
    
    public static void showToast(Activity a, CharSequence message) {
	Toast toast = Toast.makeText(a, message, Toast.LENGTH_SHORT);
	toast.setGravity(Gravity.CENTER, 0, 0);
	toast.show();
    }
    
    public static void showToast(Activity a, int messageId) {
	CharSequence message = a.getResources().getText(messageId);
	showToast(a, message);
    }
    
    public static void showYesNoConfirmation(Activity a, int title, int message, DialogInterface.OnClickListener listener) {
	new AlertDialog.Builder(a)
		.setIcon(android.R.drawable.ic_dialog_alert) // TODO: Use question mark
		.setTitle(title)
		.setMessage(message)
		.setNegativeButton(R.string.no, listener) // FIXME: supposed to be in the resources...
		.setPositiveButton(R.string.yes, listener) // FIXME: supposed to be in the resources...
		.show();
    }
}
