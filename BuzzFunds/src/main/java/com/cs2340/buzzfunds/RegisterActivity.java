package com.cs2340.buzzfunds;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity {
	Intent intent = getIntent();
	private EditText mUsernameView;
	private EditText mPasswordView;
	private EditText mVerifyView;
	private String mUsername;
	private String mPassword;
	private String mVerify;
	private UserRegisterTask mRegisterTask;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		Setup.ignoreMainNetworkException();
		
		mUsernameView = (EditText) findViewById(R.id.register_username);
		mPasswordView = (EditText) findViewById(R.id.register_password);
		mVerifyView = (EditText) findViewById(R.id.register_verify);
		
		findViewById(R.id.registerbutton).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptRegister();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	/**
	 * Attempts to register the account specified by the login form.
	 * If there are form errors (invalid username, missing fields, etc.), the
	 * errors are presented and no actual register attempt is made.
	 */
	public void attemptRegister() {
        // Button click
        SoundEffect.playSound(getApplicationContext(), R.raw.click);

		// Reset errors.
		mUsernameView.setError(null);
		mPasswordView.setError(null);
		mVerifyView.setError(null);
		
		// Store values at the time of the login attempt.
		mUsername = mUsernameView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mVerify = mVerifyView.getText().toString();
		
		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(Html.fromHtml("<font color='red'>" + getString(R.string.error_field_required) + "</font>"));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(Html.fromHtml("<font color='red'>" + getString(R.string.error_invalid_password) + "</font>"));
			focusView = mPasswordView;
			cancel = true;
		} else if (!mPassword.equals(mVerify)) {
			// TODO: Error string if password doesn't match verify field
			// mPasswordView.setError(Html.fromHtml("<font color='red'>" + getString(R.string.error_verify_does_not_match) + "</font>"));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid username.
		if (TextUtils.isEmpty(mUsername)) {
			mUsernameView.setError(getString(R.string.error_field_required));
			focusView = mUsernameView;
			cancel = true;
		} // TODO: else if (usernameExistsOnServer) { ... }
		
		if (cancel) {
			// There was an error; don't attempt register and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.

			// TODO: Show progress
			
			mRegisterTask = new UserRegisterTask();
			mRegisterTask.execute((Void) null);
			try {
				if (mRegisterTask.get()) {
					Intent success = new Intent(this, AccountOverviewActivity.class);
					//success.putExtra("AUTH_STATE", true);
					//success.putExtra("USERNAME", mUsername);
					IntentSingleton.putBoolean("AUTH_STATE", true);
					IntentSingleton.putString("USERNAME", mUsername);
					startActivity(success);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * Represents an asynchronous registration task used to 
	 * authenticate the user.
	 */
	public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
		
		@Override
		protected Boolean doInBackground(Void... params) {
			return Authenticator.RegisterNewUser(mUsername, mPassword);
		}
		
		@Override
		protected void onPostExecute(final Boolean success) {
			mRegisterTask = null;

			if (success) {
				finish();
			} else {
				mUsernameView
						.setError(Html.fromHtml("<font color='red'>" + getString(R.string.error_username_exists) + "</font>"));
				mUsernameView.requestFocus();
			}
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
