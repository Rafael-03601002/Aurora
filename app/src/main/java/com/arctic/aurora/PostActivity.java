package com.arctic.aurora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

public class PostActivity extends AppCompatActivity {

    private TextInputEditText description;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ImageView confirm = findViewById(R.id.explore_post_confirm);
        ImageView cancel = findViewById(R.id.explore_post_cancel);
        image = findViewById(R.id.explore_post_image);
        description = findViewById(R.id.explore_post_description);

        confirm.setOnClickListener(btn_confirm);
        cancel.setOnClickListener(btn_cancel);
        image.setOnClickListener(btn_image);
    }

    private View.OnClickListener btn_confirm = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String desc = String.valueOf(description.getText());
        }
    };
    private View.OnClickListener btn_cancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener btn_image = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}