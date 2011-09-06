//package com.liongrid.infectomancer;
//
//import com.liongrid.gameengine.LUpgrade;
//import com.liongrid.gameengine.LTalentIcon;
//import com.liongrid.gameengine.LTalentTree;
//import com.liongrid.gameengine.LTalentTree.OnSelectedChangeListener;
//import com.liongrid.infectomancer.R;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.TextView;
//
//public class IUpgradeActivity extends Activity {
//	private LTalentTree talentTree;
//	private TextView upgradeText;
//	private Button upgradeButton;
//	private TextView upgradeInfoText;
//	private TextView upgradeStateText;
//	private TextView coinText;
//	private CheckBox cheatBox;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.upgrade);
//
//		talentTree = (LTalentTree) findViewById(R.id.talentTree1);
//		upgradeText = (TextView) findViewById(R.id.upgradeDescriptionText);
//		upgradeButton = (Button) findViewById(R.id.purchaseUpgradeButton);
//		upgradeInfoText = (TextView) findViewById(R.id.upgradeInfoText);
//		upgradeStateText = (TextView) findViewById(R.id.currentUpgradeStateText);
//		coinText = (TextView) findViewById(R.id.coinText);
//		cheatBox = (CheckBox) findViewById(R.id.cheatCheck);
//		talentTree.setOnSelectedChangeListener(new SelectedChangeListener());
//
//		upgradeButton.setOnClickListener(new ClickListener());
//
//	}
//
//	private void updateRightPanel(){
//		int checkedID = talentTree.getSelectedId();
//		LTalentIcon uTB = (LTalentIcon) findViewById(checkedID);
//		if(uTB == null) return;
//
//		LUpgrade<?> upgrade = uTB.getUpgrade();
//
//		int coins = IGamePointers.coins;
//		int price = upgrade.getUpgradePrice();
//
//		//Set the description text
//		upgradeText.setText(upgrade.getDescriptionRes());
//
//		//Make sure player can`t get awesome upgrades 
//		//until they have enough others.
//		upgradeButton.setEnabled(cheatBox.isChecked() || (uTB.isUpgradeable() && coins >= price));
//
//		upgradeInfoText.setText("Price: "+price);
//
//		upgradeStateText.setText(upgrade.getCurrentStateDescription());
//
//		coinText.setText("Coins: " + coins);
//	}
//
//
//
//	private class SelectedChangeListener implements OnSelectedChangeListener{
//
//		public void onSelectedChanged(LTalentTree tTree, int selectedId) {
//			updateRightPanel();
//		}
//
//	}
//
//	private class ClickListener implements OnClickListener{
//
//		public void onClick(View arg0) {
//
//			int checkedID = talentTree.getSelectedId();
//			LTalentIcon uTB = (LTalentIcon) findViewById(checkedID);
//			if(uTB == null) return;
//			LUpgrade<?> upgrade = uTB.getUpgrade();
//			if(!cheatBox.isChecked()){
//				IGamePointers.coins -= upgrade.getUpgradePrice();
//			}
//			upgrade.incrementRank();
//
//			updateRightPanel();
//		}
//
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		IGameActivity.saveData(getApplicationContext());
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		IGameActivity.loadData(getApplicationContext());
//	}
//
//}
