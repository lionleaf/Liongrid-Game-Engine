package com.liongrid.gameengine.tools;

import java.lang.reflect.Field;

public class ResIdReflector {
	/**
	 * Dangerous! Using undocumented API, might change!
	 * 
	 * @param variableName
	 * @param c
	 * @return
	 */
	public static int getResId(String variableName, Class<?> c) {
	    try {
	        Field idField = c.getDeclaredField(variableName.toLowerCase());
	        return idField.getInt(idField);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return -1;
	    } 
	}
}
