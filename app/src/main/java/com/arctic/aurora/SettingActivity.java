package com.arctic.aurora;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {

    AlertDialog.Builder alertDialog;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button logout = findViewById(R.id.btn_logout);
        logout.setOnClickListener(btn_logout);
        ImageView btnReturn = findViewById(R.id.setting_return);
        btnReturn.setOnClickListener(btn_return);

    }

    private final View.OnClickListener btn_logout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            alertDialog = new AlertDialog.Builder(SettingActivity.this);
            View custom_alert = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
            alertDialog.setView(custom_alert);
            dialog = alertDialog.create();
            dialog.show();
            Button btn_confirm = custom_alert.findViewById(R.id.btn_confirm_logout);
            Button btn_cancel = custom_alert.findViewById(R.id.btn_cancel);
            TextView intro = custom_alert.findViewById(R.id.confirm_dialog_intro);
            intro.setText(getString(R.string.title_logout));

            btn_confirm.setOnClickListener(btn_confirm_logout);
            btn_cancel.setOnClickListener(btn_cancel_logout);
        }
    };
    private final View.OnClickListener btn_return = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private final View.OnClickListener btn_confirm_logout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(SettingActivity.this, LoginActivity.class));
        }
    };
    private final View.OnClickListener btn_cancel_logout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.cancel();
        }
    };
}