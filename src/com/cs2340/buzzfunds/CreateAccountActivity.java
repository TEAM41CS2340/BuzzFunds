package com.cs2340.buzzfunds;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class CreateAccountActivity extends Activity {

	EditText mAccountId;
	RadioButton mAccountTypeSavings;
	RadioButton mAccountTypeChecking;
	RadioGroup mNewAccountType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
		mAccountId = (EditText) findViewById(R.id.create_account_newId);
		mAccountTypeSavings = (RadioButton) findViewById(R.id.create_account_savings);
		//mAccountTypeChecking = (RadioButton) findViewById(R.id.create_account_checking);
		mNewAccountType = (RadioGroup) findViewById(R.id.newAccountType);
	}
	
	private String getAccountType() {
		int selectedId = mNewAccountType.getCheckedRadioButtonId();
		RadioButton checked = (RadioButton) findViewById(selectedId);
		if (checked.getText().equals(mAccountTypeSavings.getText())) {
			return "savings";
		} else {
			return "checking";
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_account, menu);
		return true;
	}
	
	public void createAccount(View view) {
		if (submitAccount()) {
			// Successful, transition back to AccountOverviewActivity
			Intent intent = new Intent(this, AccountOverviewActivity.class);
			startActivity(intent);
		} else {
			mAccountId.setError(getString(R.string.error_account_create));
		}
	}
	
	public boolean submitAccount() {
		boolean result = false;
		if (IntentSingleton.getString("USERNAME") != null) {
			Authenticator auth = new Authenticator(DefaultConnection.BUZZFUNDS, 
					IntentSingleton.getString("USERNAME"));
			//Account newAccount = auth.httpGetAddAccount(mAccountId.getText().toString(), 
					//getAccountType());
			if (auth.httpGetAddAccount(mAccountId.getText().toString(), getAccountType(), "1")) {
				result = true;
			}
		}
		
		return result;
	}

}