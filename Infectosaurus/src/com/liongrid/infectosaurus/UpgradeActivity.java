package com.liongrid.infectosaurus;

import com.liongrid.gameengine.Upgrade;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class UpgradeActivity extends Activity {
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 
		 setContentView(R.layout.upgrade);
		 
		 final UpgradeTreeGroup upgradeGroup = (UpgradeTreeGroup) findViewById(R.id.upgradeTreeGroup);
		 final TextView upgradeText = (TextView) findViewById(R.id.upgradeDescriptionText);
		 upgradeGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int checkedID = group.getCheckedRadioButtonId();
				UpgradeTreeButton uTB = (UpgradeTreeButton) findViewById(checkedID);
				Upgrade<?> upgrade = uTB.getUpgrade();
				upgradeText.setText(upgrade.getDescriptionRes());
			}
		});
		 
	 }
}
