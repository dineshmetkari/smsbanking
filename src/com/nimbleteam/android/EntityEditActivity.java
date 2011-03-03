package com.nimbleteam.android;

import android.os.Bundle;

public abstract class EntityEditActivity extends EditActivity {
    private Long entityId;

    public EntityEditActivity(int title, int contentView) {
	super(title, contentView);
    }
    
    protected Long getEntityId() {
	return entityId;
    }
    
    protected void setEntityId(Long entityId) {
	this.entityId = entityId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	if (savedInstanceState != null) {
	    entityId = (Long) savedInstanceState.getSerializable(Entity.KEY_ID);
	}
	
	if (entityId == null) {
	    Bundle extras = getIntent().getExtras();
	    entityId = extras != null ? extras.getLong(Entity.KEY_ID) : null;
	}

	super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
	super.onSaveInstanceState(outState);
	outState.putSerializable(Entity.KEY_ID, entityId);
    }
}
