package com.liongrid.infectosaurus;

import com.liongrid.gameengine.Upgrade;
import com.liongrid.infectosaurus.TalentTree.OnSelectedChangeListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class UpgradeActivity extends Activity {
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 
		 setContentView(R.layout.upgrade);
		 
		 
		 final TalentTree talentTree = (TalentTree) findViewById(R.id.talentTree1);
		 final TextView upgradeText = (TextView) findViewById(R.id.upgradeDescriptionText);
		 final Button upgradeButton = (Button) findViewById(R.id.purchaseUpgradeButton);
		 final TextView upgradeInfoText = (TextView) findViewById(R.id.upgradeInfoText);
		 
		 
		 talentTree.setOnSelectedChangeListener(new OnSelectedChangeListener() {
			 
			public void onSelectedChanged(TalentTree tTree, int selectedId) {
				int checkedID = tTree.getSelectedId();
				TalentIcon uTB = (TalentIcon) findViewById(checkedID);
				if(uTB == null) return;
				
				Upgrade<?> upgrade = uTB.getUpgrade();
				
				//Set the description text
				upgradeText.setText(upgrade.getDescriptionRes());
				
				//Make sure player can`t get awesome upgrades 
				//until they have enough others.
				upgradeButton.setEnabled(uTB.isUpgradeable());
				
				upgradeInfoText.setText("Price: "+upgrade.getUpgradePrice());
				
			}
		});
		 
		 upgradeButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				int checkedID = talentTree.getSelectedId();
				TalentIcon uTB = (TalentIcon) findViewById(checkedID);
				if(uTB == null) return;
				
				Upgrade<?> upgrade = uTB.getUpgrade();
				
				upgrade.incrementRank();

				
			}
		});
		 
	 }
}
