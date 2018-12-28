package com.prowidgetstudio.gitstatsmvp.customViews;

/**
 * Created by Dzano on 20.11.2018.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.prowidgetstudio.gitstatsmvp.R;
import com.prowidgetstudio.gitstatsmvp.home.OnLogOutDialogClickListener;

import androidx.annotation.NonNull;


public class LogOutDialog extends Dialog implements View.OnClickListener {

    OnLogOutDialogClickListener dialogListener;

    public LogOutDialog(@NonNull Context cont, OnLogOutDialogClickListener dialogListener) {
        super(cont);
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.logout_dialog);
        Button yes = (Button) findViewById(R.id.yes);
        Button no = (Button) findViewById(R.id.no);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.yes:
                dialogListener.onYes();
                dismiss();
                break;

            case R.id.no:
                dismiss();
                break;
        }

    }
}