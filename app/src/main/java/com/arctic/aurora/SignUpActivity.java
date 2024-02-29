package com.arctic.aurora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button signup = findViewById(R.id.btn_signup);
        signup.setOnClickListener(btn_signup);
    }

    private final View.OnClickListener btn_signup = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println(v);
        }
    };
}