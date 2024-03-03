package com.arctic.aurora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
            finish();
        }
    }

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
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
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
            // animation progressbar visible
            progressBar.setVisibility(View.VISIBLE);

            // Get string from text object
            TextInputEditText text_email = findViewById(R.id.text_login_email);
            TextInputEditText text_password = findViewById(R.id.text_login_password);
            String email = String.valueOf(text_email.getText());
            String password = String.valueOf(text_password.getText());

            // Firebase authentication
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Email or Password is empty", Toast.LENGTH_SHORT).show();
            }
            else {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    };
}