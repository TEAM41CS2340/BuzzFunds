package com.cs2340.buzzfunds;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CreateAccountActivity extends Activity {

	EditText mAccountId, mInterestRate;
	RadioButton mAccountTypeSavings;
	RadioButton mAccountTypeChecking;
	RadioGroup mNewAccountType;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        //user = (User) getIntent().getSerializableExtra("user");
        user = IntentSingleton.getUser("USER");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
		mAccountId = (EditText) findViewById(R.id.create_account_newId);
        mInterestRate = (EditText) findViewById(R.id.newAccountRate);
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
        // Button click
        SoundEffect.playSound(getApplicationContext(), R.raw.click);

        String shortName = mAccountId.getText().toString();
        String type = getAccountType();
        double balance = 0;
        double interest = Double.parseDouble(mInterestRate.getText().toString());

		if (user.AddAccount(shortName, balance, type, interest)) {
			// Successful, transition back to AccountOverviewActivity
			Intent intent = new Intent(this, AccountOverviewActivity.class);
			startActivity(intent);
		} else {
			mAccountId.setError(getString(R.string.error_account_create));
		}
	}

    public void displayHelp(View view) {
        TextView helpButton = (TextView) findViewById(R.id.helpView);
        TextView helpText = (TextView) findViewById(R.id.helpText);
        if (helpText.getVisibility() == View.INVISIBLE) {
            helpButton.setText(R.string.global_help_label_off);
            helpText.setVisibility(View.VISIBLE);
        } else {
            helpButton.setText(R.string.global_help_label);
            helpText.setVisibility(View.INVISIBLE);
        }
    }
}
