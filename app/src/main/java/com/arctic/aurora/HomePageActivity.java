package com.arctic.aurora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePageActivity extends AppCompatActivity {

    public Button mart;
    public Button program;
    public Button explore;
    private ConstraintLayout base_view;
    private View layout_program;


    @Override
    public void onStart() {
        super.onStart();
        groupSetBtnColor(program, new Button[]{mart, explore});
        changeView(layout_program);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // System auto-generated
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Firebase user authentication
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Custom events
        mart = findViewById(R.id.btn_mart);
        mart.setOnClickListener(btn_mart);
        program = findViewById(R.id.btn_program);
        program.setOnClickListener(btn_program);
        explore = findViewById(R.id.btn_explore);
        explore.setOnClickListener(btn_explore);

        base_view = findViewById(R.id.custom_ConstraintLayout);
        layout_program = getLayoutInflater().inflate(R.layout.custom_program, null);
        ImageView setting = layout_program.findViewById(R.id.setting);
        setting.setOnClickListener(btn_setting);



        if (currentUser == null) {
            startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
            finish();
        }
    }

    // base events
    public View.OnClickListener btn_mart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(mart, new Button[]{program, explore});
        }
    };
    public View.OnClickListener btn_program = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(program, new Button[]{mart, explore});
        }
    };
    public View.OnClickListener btn_explore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(explore, new Button[]{mart, program});
        }
    };

    // custom_program page events
    public View.OnClickListener btn_setting = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(HomePageActivity.this, SettingActivity.class));
        }
    };

    // Common function
    public void groupSetBtnColor(Button white_btn, Button[] gray_btn) {
        setBtnColor(white_btn, R.color.white);
        for (Button btn : gray_btn) {
            setBtnColor(btn, R.color.gray_light);
        }
    }
    public void setBtnColor(Button btn, int colorId) {
        btn.setTextColor(getColor(colorId));
        ((MaterialButton) btn).setIconTint(getColorStateList(colorId));
    }
    public void changeView(View view){
        base_view.removeAllViews();
        base_view.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}