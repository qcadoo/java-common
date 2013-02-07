package com.mobeelizer.java.conflict;

import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import com.mobeelizer.java.sync.MobeelizerJsonEntity;

public class MobeelizerConflictVersionJson extends MobeelizerJsonEntity {

    private String user;
    
    private String device;
    
    private Date date;
    
	public MobeelizerConflictVersionJson(String json) throws JSONException{
		super(json);
		JSONObject jsonObject = new JSONObject(json);
        user = jsonObject.getString("user");
        date =  new Date(jsonObject.getLong("date"));
        if(jsonObject.has("device")){
        	device = jsonObject.getString("device");
        }
	}
	
	public Date getDate(){
		return date;
	}
	
	public String getUser(){
		return user;
	}
	
	public String getDevice(){
		return device;
	}
}
