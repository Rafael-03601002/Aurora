package com.arctic.aurora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class HomePageActivity extends AppCompatActivity {
    // home page (base) objects
    public Button mart;
    public Button program;
    public Button explore;
    private ConstraintLayout base_view;

    // custom_program objects
    private View layout_program;
    private View layout_mart;
    private View layout_explore;
    private LinearLayout program_view;
    public Button monday;
    public Button tuesday;
    public Button wednesday;
    public Button thursday;
    public Button friday;
    public Button saturday;
    public Button sunday;

    // confirm_program_dialog objects
    private TextInputEditText sports;
    private TextInputEditText time;
    private AlertDialog programDialog;

    // program element
    CountDownTimer countDownTimer;

    @Override
    public void onStart() {
        super.onStart();
        groupSetBtnColor(program, new Button[]{mart, explore});
        changeView(layout_program);

        int CurrentTime = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        switch (CurrentTime) {
            case 1:
                sunday.performClick();
                break;
            case 2:
                monday.performClick();
                break;
            case 3:
                tuesday.performClick();
                break;
            case 4:
                wednesday.performClick();
                break;
            case 5:
                thursday.performClick();
                break;
            case 6:
                friday.performClick();
                break;
            case 7:
                saturday.performClick();
                break;
        }
    }

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // System auto-generated
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Firebase user authentication
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
            finish();
        }

        // home page elements (base)
        base_view = findViewById(R.id.custom_ConstraintLayout);

        mart = setBtnEvent(null, R.id.btn_mart, btn_mart);
        program = setBtnEvent(null, R.id.btn_program, btn_program);
        explore = setBtnEvent(null, R.id.btn_explore, btn_explore);

        // custom_program elements
        layout_program = getLayoutInflater().inflate(R.layout.custom_program, null);
        program_view = layout_program.findViewById(R.id.program_list);

        ImageView setting = setImageViewEvent(layout_program, R.id.setting, btn_setting);
        monday = setBtnEvent(layout_program, R.id.btn_monday, btn_monday);
        tuesday = setBtnEvent(layout_program, R.id.btn_tuesday, btn_tuesday);
        wednesday = setBtnEvent(layout_program, R.id.btn_wednesday, btn_wednesday);
        thursday = setBtnEvent(layout_program, R.id.btn_thursday, btn_thursday);
        friday = setBtnEvent(layout_program, R.id.btn_friday, btn_friday);
        saturday = setBtnEvent(layout_program, R.id.btn_saturday, btn_saturday);
        sunday = setBtnEvent(layout_program, R.id.btn_sunday, btn_sunday);
        Button add_program = setBtnEvent(layout_program, R.id.add_program, btn_add_program);

        // custom_mart elements
        layout_mart = getLayoutInflater().inflate(R.layout.custom_mart, null);

        // custom_explore elements
        layout_explore = getLayoutInflater().inflate(R.layout.custom_explore, null);
    }

    // base events
    public View.OnClickListener btn_mart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(mart, new Button[]{program, explore});
            changeView(layout_mart);
        }
    };
    public View.OnClickListener btn_program = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(program, new Button[]{mart, explore});
            changeView(layout_program);
        }
    };
    public View.OnClickListener btn_explore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(explore, new Button[]{mart, program});
            changeView(layout_explore);
        }
    };

    // custom_program page events
    public View.OnClickListener btn_setting = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(HomePageActivity.this, SettingActivity.class));
        }
    };
    public View.OnClickListener btn_monday = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(monday, new Button[]{tuesday, wednesday, thursday, friday, saturday, sunday});
        }
    };
    public View.OnClickListener btn_tuesday = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(tuesday, new Button[]{monday, wednesday, thursday, friday, saturday, sunday});
        }
    };
    public View.OnClickListener btn_wednesday = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(wednesday, new Button[]{monday, tuesday, thursday, friday, saturday, sunday});
        }
    };
    public View.OnClickListener btn_thursday = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(thursday, new Button[]{monday, tuesday, wednesday, friday, saturday, sunday});
        }
    };
    public View.OnClickListener btn_friday = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(friday, new Button[]{monday, tuesday, wednesday, thursday, saturday, sunday});
        }
    };
    public View.OnClickListener btn_saturday = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(saturday, new Button[]{monday, tuesday, wednesday, thursday, friday, sunday});
        }
    };
    public View.OnClickListener btn_sunday = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(sunday, new Button[]{monday, tuesday, wednesday, thursday, friday, saturday});
        }
    };
    public View.OnClickListener btn_add_program = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // custom alert dialog
            AlertDialog.Builder programDialogBuilder = new AlertDialog.Builder(HomePageActivity.this);
            View custom_confirm_program = getLayoutInflater().inflate(R.layout.custom_confirm_program_dialog, null);
            programDialogBuilder.setView(custom_confirm_program);
            programDialog = programDialogBuilder.create();
            programDialog.show();

            // alert dialog elements and events
            sports = custom_confirm_program.findViewById(R.id.sports);
            time = custom_confirm_program.findViewById(R.id.text_timer);
            Button confirm = setBtnEvent(custom_confirm_program, R.id.btn_dialog_confirm, btn_dialog_confirm);
            Button cancel = setBtnEvent(custom_confirm_program, R.id.btn_dialog_cancel, btn_dialog_cancel);
        }
    };
    public View.OnClickListener btn_dialog_confirm = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String sports_name = String.valueOf(sports.getText());
            String timer = String.valueOf(time.getText());

            View layout_program_dtl = getLayoutInflater().inflate(R.layout.program, null);
            TextView sport_name = layout_program_dtl.findViewById(R.id.program_label);
            TextView text_timer = layout_program_dtl.findViewById(R.id.timer);
            ImageView program_playOrStop = layout_program_dtl.findViewById(R.id.btn_timer_control);
            ImageView program_delete = layout_program_dtl.findViewById(R.id.btn_delete);

            sport_name.setText(sports_name);
            text_timer.setText(timer);
            program_view.addView(layout_program_dtl);
            programDialog.cancel();

            // add onclick listener
            program_playOrStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            program_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    program_view.removeView(layout_program_dtl);
                }
            });
        }
    };
    public View.OnClickListener btn_dialog_cancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            programDialog.cancel();
        }
    };

    // Common function
    public Button setBtnEvent(View view , int id, View.OnClickListener event) {
        Button btn;
        if (view == null) {
            btn = findViewById(id);
            btn.setOnClickListener(event);
        }
        else {
            btn = view.findViewById(id);
            btn.setOnClickListener(event);
        }
        return btn;
    }
    public ImageView setImageViewEvent(View view, int id, View.OnClickListener event) {
        ImageView imageView;
        if (view == null) {
            imageView = findViewById(id);
            imageView.setOnClickListener(event);
        }
        else {
            imageView = view.findViewById(id);
            imageView.setOnClickListener(event);
        }
        return imageView;
    }
    public void groupSetBtnColor(Button white_btn, @NonNull Button[] gray_btn) {
        setBtnColor(white_btn, R.color.white);
        for (Button btn : gray_btn) {
            setBtnColor(btn, R.color.gray_light);
        }
    }
    public void setBtnColor(@NonNull Button btn, int colorId) {
        btn.setTextColor(getColor(colorId));
        ((MaterialButton) btn).setIconTint(getColorStateList(colorId));
    }
    public void changeView(View view){
        base_view.removeAllViews();
        base_view.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}