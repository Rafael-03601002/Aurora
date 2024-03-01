package com.arctic.aurora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // System auto-generated
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Custom events
        Button register = findViewById(R.id.btn_register);
        register.setOnClickListener(btn_register);

        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(btn_login);
    }

    private final View.OnClickListener btn_register = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // jump to SignUpActivity
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        }
    };

    private final View.OnClickListener btn_login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Get string from text object
            TextInputEditText text_email = findViewById(R.id.text_login_email);
            TextInputEditText text_password = findViewById(R.id.text_login_password);
            String email = String.valueOf(text_email.getText());
            String password = String.valueOf(text_password.getText());

            // check value exist
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(LoginActivity.this, "Email or Password is empty", Toast.LENGTH_SHORT).show();
            }

            // Firebase authentication
        }
    };
}