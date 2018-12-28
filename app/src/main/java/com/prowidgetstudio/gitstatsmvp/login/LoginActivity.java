package com.prowidgetstudio.gitstatsmvp.login;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.prowidgetstudio.gitstatsmvp.BaseActivity;
import com.prowidgetstudio.gitstatsmvp.R;
import com.prowidgetstudio.gitstatsmvp.repository.RepositoryImpl;
import com.prowidgetstudio.gitstatsmvp.utils.Errors;
import com.prowidgetstudio.gitstatsmvp.utils.Utils;
import com.prowidgetstudio.gitstatsmvp.home.TabbedActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

/**
 * Created by Dzano on 1.12.2018.
 */

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener{

    private LoginPresenterImpl presenter;

    private ProgressBar progress;
    private FrameLayout zatamni;
    private EditText username, password, url;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {

        // portrait / landscape mode
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        url = (EditText) findViewById(R.id.url);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        zatamni = (FrameLayout) findViewById(R.id.zatamni);
        layout = (ConstraintLayout) findViewById(R.id.layout);
        Button loginButton = (Button) findViewById(R.id.login);

        loginButton.setOnClickListener(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        RepositoryImpl repository = new RepositoryImpl(prefs, null);
        presenter = new LoginPresenterImpl(this, new LoginInteractorImpl(), repository);

        // provjeri da li je vec prijavljen i da li je ReadMe uspjesno ucitan
        presenter.start();
    }

    private void disableTouch() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void enableTouch() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void showLoading() {

        disableTouch();
        progress.setVisibility(View.VISIBLE);
        zatamni.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        enableTouch();
        progress.setVisibility(View.GONE);
        zatamni.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError(Errors code) {

        if (username != null) {
            if(code.getId() == Errors.ENTER_USERNAME.getId()) {
                username.setError(getResources().getString(R.string.usernameError));
            }else if (code.getId() == Errors.USERNAME_PASS_INVALID.getId()){
                username.setError(getResources().getString(R.string.usernamePassError));
            }
        }
    }

    @Override
    public void setPasswordError(Errors code){

        if (password != null) {
            if(code.getId() == Errors.ENTER_PASSWORD.getId()) {
                password.setError(getResources().getString(R.string.passwordError));
            }
        }
    }

    @Override
    public void setUrlError(Errors code){

        if (url != null) {
            if(code.getId() == Errors.WRONG_URL.getId()) {
                url.setError(getResources().getString(R.string.urlError));
            }
        }
    }


    @Override
    public void loginFailure(Errors code) {

        if(code.getId() == 5)
            errorMsg(R.string.loginFail);
        else
            Toast.makeText(this, getResources().getString(R.string.usernamePassError), Toast.LENGTH_LONG).show();
    }


    @Override
    public void loggedIn(){
        Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login:

                if (username != null && password != null && url != null) {
                    if (Utils.isNetworkAvailable(LoginActivity.this)) {

                        String validUrl = Utils.gitURL(url.getText().toString().trim());
                        presenter.callLogin(username.getText().toString().trim(), password.getText().toString(), validUrl);
                    } else {
                        errorMsg(R.string.noConnection);
                    }
                }
                break;
        }
    }

    private void errorMsg(int stringResId) {

        Snackbar snackBar = Snackbar.make(layout, this.getResources().getString(stringResId), 10000);
        snackBar.getView().setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.crvena));
        snackBar.show();
    }
}
