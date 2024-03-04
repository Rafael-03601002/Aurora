package com.arctic.aurora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {

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
            FirebaseAuth.getInstance().signOut();
        }
    };
    private final View.OnClickListener btn_return = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}