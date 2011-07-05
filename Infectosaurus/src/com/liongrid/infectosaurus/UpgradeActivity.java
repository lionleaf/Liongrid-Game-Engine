package com.liongrid.infectosaurus;

import com.liongrid.gameengine.Upgrade;
import com.liongrid.infectosaurus.TalentTree.OnSelectedChangeListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class UpgradeActivity extends Activity {
	private TalentTree talentTree;
	private TextView upgradeText;
	private Button upgradeButton;
	private TextView upgradeInfoText;
	private TextView upgradeStateText;
	private TextView coinText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.upgrade);
		
		talentTree = (TalentTree) findViewById(R.id.talentTree1);
		upgradeText = (TextView) findViewById(R.id.upgradeDescriptionText);
		upgradeButton = (Button) findViewById(R.id.purchaseUpgradeButton);
		upgradeInfoText = (TextView) findViewById(R.id.upgradeInfoText);
		upgradeStateText = (TextView) findViewById(R.id.currentUpgradeStateText);
		coinText = (TextView) findViewById(R.id.coinText);
		talentTree.setOnSelectedChangeListener(new SelectedChangeListener());

		upgradeButton.setOnClickListener(new ClickListener());

	}
	
	private void updateRightPanel(){
		int checkedID = talentTree.getSelectedId();
		TalentIcon uTB = (TalentIcon) findViewById(checkedID);
		if(uTB == null) return;

		Upgrade<?> upgrade = uTB.getUpgrade();

		int coins = InfectoPointers.coins;
		int price = upgrade.getUpgradePrice();
		
		//Set the description text
		upgradeText.setText(upgrade.getDescriptionRes());

		//Make sure player can`t get awesome upgrades 
		//until they have enough others.
		upgradeButton.setEnabled(uTB.isUpgradeable() && coins >= price );

		upgradeInfoText.setText("Price: "+price);
		
		upgradeStateText.setText(upgrade.getCurrentStateDescription());
		
		coinText.setText("Coins: " + coins);
	}
	


	private class SelectedChangeListener implements OnSelectedChangeListener{

		public void onSelectedChanged(TalentTree tTree, int selectedId) {
			updateRightPanel();
		}

	}
	
	private class ClickListener implements OnClickListener{

		public void onClick(View arg0) {
			
			int checkedID = talentTree.getSelectedId();
			TalentIcon uTB = (TalentIcon) findViewById(checkedID);
			if(uTB == null) return;
			Upgrade<?> upgrade = uTB.getUpgrade();
			
			InfectoPointers.coins -= upgrade.getUpgradePrice();
			upgrade.incrementRank();
			
			updateRightPanel();
		}
		
	}

}
