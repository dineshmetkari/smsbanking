package com.nimbleteam.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nimbleteam.smsbanking.R;

public abstract class EditActivity extends Activity {
    private int title;
    private int contentView;
    
    public EditActivity(int title, int contentView) {
	super();
	this.title = title;
	this.contentView = contentView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	setTitle(title);
	setContentView(contentView);
	
	Button saveButton = (Button) findViewById(R.id.save);

	loadData();

	saveButton.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View view) {
		if (validateData()) {
		    saveData();
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
    
    protected abstract void loadData();
    
    protected boolean validateData() { return true; }
    
    protected abstract void saveData();
}
