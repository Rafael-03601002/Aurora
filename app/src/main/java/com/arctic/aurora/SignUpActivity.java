package com.arctic.aurora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // System auto-generated
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Custom events
        Button signup = findViewById(R.id.btn_signup);
        signup.setOnClickListener(btn_signup);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        ImageView return_to_login = findViewById(R.id.return_login);
        return_to_login.setOnClickListener(btn_return_login);
    }

    private final View.OnClickListener btn_signup = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // animation progressbar visible
            progressBar.setVisibility(View.VISIBLE);

            // Firebase register user
            TextInputEditText text_username = findViewById(R.id.text_username);
            TextInputEditText text_email = findViewById(R.id.text_email);
            TextInputEditText text_password = findViewById(R.id.text_password);

            String username = String.valueOf(text_username.getText());
            String email = String.valueOf(text_email.getText());
            String password = String.valueOf(text_password.getText());

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SignUpActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            }
            else {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // animation progressbar gone
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            // Sign up success and return back to LoginActivity
                            Toast.makeText(SignUpActivity.this, "Register Success, your account is created", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            // Sign up failed
                            Toast.makeText(SignUpActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
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