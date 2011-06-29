package com.liongrid.infectosaurus.components;


import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.liongrid.gameengine.Component;
import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.InfectoGameObject;

/**
 * @author Lionleaf
 *	A quick fix to show Furu that it`s easily done!
 *	
 */
public class TiltMovementComponent extends Component<InfectoGameObject> {
	private float[] mLastValues;
	
	public TiltMovementComponent(){
		SensorManager manager = (SensorManager) 
		gamePointers.panel.getContext().getSystemService(Context.SENSOR_SERVICE);

		if(manager == null) Log.d("OH", "SHIT");
		manager.registerListener(new mListener(), 
			manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
			SensorManager.SENSOR_DELAY_GAME);
	}
	
	@Override
	public void update(float dt, InfectoGameObject parent) {
		if(mLastValues == null) return;
		parent.vel.x = mLastValues[1];
		parent.vel.y = -mLastValues[0];
		float speed = parent.speed * Math.min(2,parent.vel.length())/2f;
		
		parent.vel.normalize();
		parent.vel.multiply(speed);
	}
	
	private class mListener implements SensorEventListener{

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}

		public void onSensorChanged(SensorEvent event) {
			mLastValues = event.values.clone();
		}
		
	}
}
