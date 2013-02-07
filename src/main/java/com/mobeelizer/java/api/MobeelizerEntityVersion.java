package com.mobeelizer.java.api;

import java.util.Date;

/**
 * Entity version.
 * 
 * @since 1.7
 */
public interface MobeelizerEntityVersion {

	/**
	 * User who created this version and synchronized.
	 * 
	 * @since 1.7
	 */	
	String getUser();

	/**
	 * Date of version synchronization.
	 * 
	 * @since 1.7
	 */	
	Date getDate();
	

	/**
	 * Device class where version was created.
	 * 
	 * @since 1.7
	 */	
	String getDevice();
	

	/**
	 * Entity object.
	 * 
	 * @since 1.7
	 */	
	Object getEntity();
}
