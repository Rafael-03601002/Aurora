package com.arctic.aurora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // System auto-generated
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Custom events
        Button signup = findViewById(R.id.btn_signup);
        signup.setOnClickListener(btn_signup);

        ImageView return_to_login = findViewById(R.id.return_login);
        return_to_login.setOnClickListener(btn_return_login);
    }

    private final View.OnClickListener btn_signup = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private final View.OnClickListener btn_return_login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // return back to LoginActivity
            finish();
        }
    };
}