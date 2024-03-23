package com.arctic.aurora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class HomePageActivity extends AppCompatActivity {
    // Common object
    public FirebaseAuth mAuth;
    public FirebaseUser currentUser;

    // base objects
    public Button diet, program, explore;
    private ConstraintLayout base_view;
    private View layout_program, layout_diet, layout_explore;
    private AlertDialog confirmDialog;
    private LinearLayout program_view;
    private LinearLayout explore_view;
    private LinearLayout diet_view;

    // custom_program objects
    public Button monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    // custom_diet objects
    public Button diet_6, diet_5, diet_4, diet_3, diet_2, diet_1, diet_today;
    private TextView total_calories;

    // confirm_program_dialog objects
    private TextInputEditText text_sports, text_time;

    // confirm_diet_dialog objects
    private TextInputEditText foodName, calories_input;

    // base events
    public View.OnClickListener btn_diet = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(diet, new Button[]{program, explore});
            changeView(layout_diet);
            base_view.setTag("diet");
            diet_6.setText(String.valueOf(getDateNum(-6)));
            diet_5.setText(String.valueOf(getDateNum(-5)));
            diet_4.setText(String.valueOf(getDateNum(-4)));
            diet_3.setText(String.valueOf(getDateNum(-3)));
            diet_2.setText(String.valueOf(getDateNum(-2)));
            diet_1.setText(String.valueOf(getDateNum(-1)));
            diet_today.setText(String.valueOf(getDateNum(0)));
            total_calories.setText("0");

            diet_today.performClick();
        }
    };
    public View.OnClickListener btn_program = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(program, new Button[]{diet, explore});
            changeView(layout_program);
            base_view.setTag("program");
        }
    };
    public View.OnClickListener btn_explore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(explore, new Button[]{diet, program});
            changeView(layout_explore);
            base_view.setTag("explore");
            explore_view.removeAllViews();
            collect_exploreData();
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
            // loadProgramIfExist();
            groupSetBtnColor(monday, new Button[]{tuesday, wednesday, thursday, friday, saturday, sunday});
            program_view.setTag("Mon");
            program_view.removeAllViews();
            collect_currentDay_programData(currentUser.getEmail(), program_view.getTag().toString());
        }
    };
    public View.OnClickListener btn_tuesday = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(tuesday, new Button[]{monday, wednesday, thursday, friday, saturday, sunday});
            program_view.setTag("Tue");
            program_view.removeAllViews();
            collect_currentDay_programData(currentUser.getEmail(), program_view.getTag().toString());
        }
    };
    public View.OnClickListener btn_wednesday = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(wednesday, new Button[]{monday, tuesday, thursday, friday, saturday, sunday});
            program_view.setTag("Wed");
            program_view.removeAllViews();
            collect_currentDay_programData(currentUser.getEmail(), program_view.getTag().toString());
        }
    };
    public View.OnClickListener btn_thursday = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(thursday, new Button[]{monday, tuesday, wednesday, friday, saturday, sunday});
            program_view.setTag("Thu");
            program_view.removeAllViews();
            collect_currentDay_programData(currentUser.getEmail(), program_view.getTag().toString());
        }
    };
    public View.OnClickListener btn_friday = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(friday, new Button[]{monday, tuesday, wednesday, thursday, saturday, sunday});
            program_view.setTag("Fri");
            program_view.removeAllViews();
            collect_currentDay_programData(currentUser.getEmail(), program_view.getTag().toString());
        }
    };
    public View.OnClickListener btn_saturday = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(saturday, new Button[]{monday, tuesday, wednesday, thursday, friday, sunday});
            program_view.setTag("Sat");
            program_view.removeAllViews();
            collect_currentDay_programData(currentUser.getEmail(), program_view.getTag().toString());
        }
    };
    public View.OnClickListener btn_sunday = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            groupSetBtnColor(sunday, new Button[]{monday, tuesday, wednesday, thursday, friday, saturday});
            program_view.setTag("Sun");
            program_view.removeAllViews();
            collect_currentDay_programData(currentUser.getEmail(), program_view.getTag().toString());
        }
    };
    public View.OnClickListener btn_add_program = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // custom alert dialog
            AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(HomePageActivity.this);
            View custom_confirm_program = getLayoutInflater().inflate(R.layout.custom_confirm_dialog, null);
            confirmDialogBuilder.setView(custom_confirm_program);
            confirmDialog = confirmDialogBuilder.create();
            confirmDialog.show();

            // alert dialog elements and events
            text_sports = custom_confirm_program.findViewById(R.id.first_row);
            text_time = custom_confirm_program.findViewById(R.id.second_row);
            Button confirm = setBtnEvent(custom_confirm_program, R.id.btn_dialog_confirm, btn_dialog_confirm_add);
            Button cancel = setBtnEvent(custom_confirm_program, R.id.btn_dialog_cancel, btn_dialog_cancel);
        }
    };
    public View.OnClickListener btn_dialog_confirm_add = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!TextUtils.isEmpty(text_sports.getText()) && !TextUtils.isEmpty(text_time.getText())) {
                String sports_name = String.valueOf(text_sports.getText());
                int timer = Integer.parseInt(String.valueOf(text_time.getText()));

                addIntoProgram(false, sports_name, timer);
                confirmDialog.dismiss();    // same as dialog.cancel()
            }
            else {
                Toast.makeText(HomePageActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            }
        }
    };
    public View.OnClickListener btn_dialog_cancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            confirmDialog.cancel();
        }
    };
    public View.OnClickListener btn_dialog_confirm_update = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!TextUtils.isEmpty(text_sports.getText()) && !TextUtils.isEmpty(text_time.getText())) {
                String email = currentUser.getEmail();
                String weekday = String.valueOf(program_view.getTag());
                String sports_name = String.valueOf(text_sports.getText());
                int timer = Integer.parseInt(String.valueOf(text_time.getText()));
                String docID = String.valueOf(v.getTag());

                update_programData(email, weekday, docID, sports_name, timer);
                program_view.removeAllViews();
                collect_currentDay_programData(email, weekday);
                confirmDialog.dismiss();
            }
            else {
                Toast.makeText(HomePageActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            }
        }
    };

    // custom explore events
    public View.OnClickListener btn_add_explore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(HomePageActivity.this, PostActivity.class));
        }
    };

    // custom diet events
    public View.OnClickListener btn_diet_6 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            total_calories.setText("0");
            groupSetBtnColor(diet_6, new Button[]{diet_5, diet_4, diet_3, diet_2, diet_1, diet_today});
            String date = getDate(Calendar.DATE, -6);
            diet_view.removeAllViews();
            diet_view.setTag(date);
            collect_currentDay_dietData(currentUser.getEmail(), date);
        }
    };
    public View.OnClickListener btn_diet_5 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            total_calories.setText("0");
            groupSetBtnColor(diet_5, new Button[]{diet_6, diet_4, diet_3, diet_2, diet_1, diet_today});
            String date = getDate(Calendar.DATE, -5);
            diet_view.removeAllViews();
            diet_view.setTag(date);
            collect_currentDay_dietData(currentUser.getEmail(), date);
        }
    };
    public View.OnClickListener btn_diet_4 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            total_calories.setText("0");
            groupSetBtnColor(diet_4, new Button[]{diet_6, diet_5, diet_3, diet_2, diet_1, diet_today});
            String date = getDate(Calendar.DATE, -4);
            diet_view.removeAllViews();
            diet_view.setTag(date);
            collect_currentDay_dietData(currentUser.getEmail(), date);
        }
    };
    public View.OnClickListener btn_diet_3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            total_calories.setText("0");
            groupSetBtnColor(diet_3, new Button[]{diet_6, diet_5, diet_4, diet_2, diet_1, diet_today});
            String date = getDate(Calendar.DATE, -3);
            diet_view.removeAllViews();
            diet_view.setTag(date);
            collect_currentDay_dietData(currentUser.getEmail(), date);
        }
    };
    public View.OnClickListener btn_diet_2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            total_calories.setText("0");
            groupSetBtnColor(diet_2, new Button[]{diet_6, diet_5, diet_4, diet_3, diet_1, diet_today});
            String date = getDate(Calendar.DATE, -2);
            diet_view.removeAllViews();
            diet_view.setTag(date);
            collect_currentDay_dietData(currentUser.getEmail(), date);
        }
    };
    public View.OnClickListener btn_diet_1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            total_calories.setText("0");
            groupSetBtnColor(diet_1, new Button[]{diet_6, diet_5, diet_4, diet_3, diet_2, diet_today});
            String date = getDate(Calendar.DATE, -1);
            diet_view.removeAllViews();
            diet_view.setTag(date);
            collect_currentDay_dietData(currentUser.getEmail(), date);
        }
    };
    public View.OnClickListener btn_diet_today = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            total_calories.setText("0");
            groupSetBtnColor(diet_today, new Button[]{diet_6, diet_5, diet_4, diet_3, diet_2, diet_1});
            String date = getDate(Calendar.DATE, 0);
            diet_view.removeAllViews();
            diet_view.setTag(date);
            collect_currentDay_dietData(currentUser.getEmail(), date);
        }
    };
    public View.OnClickListener btn_add_diet = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // custom alert dialog
            AlertDialog.Builder dietDialogBuilder = new AlertDialog.Builder(HomePageActivity.this);
            View custom_diet_dialog = getLayoutInflater().inflate(R.layout.custom_diet_confirm_dialog, null);
            dietDialogBuilder.setView(custom_diet_dialog);
            confirmDialog = dietDialogBuilder.create();
            confirmDialog.show();

            // alert dialog elements and events
            foodName = custom_diet_dialog.findViewById(R.id.diet_first_row);
            calories_input = custom_diet_dialog.findViewById(R.id.diet_second_row);
            Button confirm = setBtnEvent(custom_diet_dialog, R.id.btn_diet_dialog_confirm, btn_diet_dialog_add);
            Button cancel = setBtnEvent(custom_diet_dialog, R.id.btn_diet_dialog_cancel, btn_dialog_cancel);
        }
    };
    public View.OnClickListener btn_diet_dialog_add = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!TextUtils.isEmpty(foodName.getText()) && !TextUtils.isEmpty(calories_input.getText())) {
                String str_foodName = String.valueOf(foodName.getText());
                int calories = Integer.parseInt(String.valueOf(calories_input.getText()));

                addIntoDiet(false, str_foodName, calories);
                confirmDialog.dismiss();
            }
            else {
                Toast.makeText(HomePageActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            }
        }
    };
    public View.OnClickListener btn_diet_dialog_update = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!TextUtils.isEmpty(foodName.getText()) && !TextUtils.isEmpty(calories_input.getText())) {
                String email = currentUser.getEmail();
                String date = String.valueOf(diet_view.getTag());
                String docID = String.valueOf(v.getTag());
                String str_foodName = String.valueOf(foodName.getText());
                int calories = Integer.parseInt(String.valueOf(calories_input.getText()));

                update_dietData(email, date,docID, str_foodName, calories);
                diet_view.removeAllViews();
                collect_currentDay_dietData(email, date);
                confirmDialog.dismiss();
            }
            else {
                Toast.makeText(HomePageActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            }
        }
    };


    // System Initialize
    @Override
    public void onStart() {
        super.onStart();
        String tag = String.valueOf(base_view.getTag());
        if (TextUtils.isEmpty(tag) || tag.equals("program") || tag.equals("null")) {
            program.performClick();

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
        else if (!TextUtils.isEmpty(tag) && tag.equals("explore")) {
            explore.performClick();
        }
        else {
            diet.performClick();

            // initial setting: today
            diet_today.performClick();
        }
    }

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // System auto-generated
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Firebase user authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
            finish();
        }

        // home page elements (base)
        base_view = findViewById(R.id.custom_ConstraintLayout);

        diet = setBtnEvent(null, R.id.btn_diet, btn_diet);
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

        // custom_explore elements
        layout_explore = getLayoutInflater().inflate(R.layout.custom_explore, null);
        explore_view = layout_explore.findViewById(R.id.explore_view);

        Button add_explore = setBtnEvent(layout_explore, R.id.add_explore, btn_add_explore);

        // custom_diet elements
        layout_diet = getLayoutInflater().inflate(R.layout.custom_diet, null);
        diet_view = layout_diet.findViewById(R.id.diet_list);

        ImageView dietSetting = setImageViewEvent(layout_diet, R.id.diet_setting, btn_setting);
        total_calories = layout_diet.findViewById(R.id.text_totalCalories);
        diet_6 = setBtnEvent(layout_diet, R.id.btn_diet_6, btn_diet_6);
        diet_5 = setBtnEvent(layout_diet, R.id.btn_diet_5, btn_diet_5);
        diet_4 = setBtnEvent(layout_diet, R.id.btn_diet_4, btn_diet_4);
        diet_3 = setBtnEvent(layout_diet, R.id.btn_diet_3, btn_diet_3);
        diet_2 = setBtnEvent(layout_diet, R.id.btn_diet_2, btn_diet_2);
        diet_1 = setBtnEvent(layout_diet, R.id.btn_diet_1, btn_diet_1);
        diet_today = setBtnEvent(layout_diet, R.id.btn_diet_today, btn_diet_today);
        Button add_diet = setBtnEvent(layout_diet, R.id.add_diet, btn_add_diet);
    }

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
    public void addIntoProgram(Boolean load_from_db, String sports, int count_down_time) {
        // store into db
        String email = currentUser.getEmail();
        String weekday = program_view.getTag().toString();
        if (!load_from_db) {
            if (email != null) {
                Program custom_program = new Program(sports, count_down_time);
                create_programData(email, weekday, custom_program);
            }
        }


        // objects
        @SuppressLint("InflateParams") View layout_program_dtl = getLayoutInflater().inflate(R.layout.custom_program_dtl, null);
        TextView sport_name = layout_program_dtl.findViewById(R.id.program_label);
        TextView text_timer = layout_program_dtl.findViewById(R.id.timer);
        ImageView program_playOrStop = layout_program_dtl.findViewById(R.id.btn_timer_control);
        ImageView program_delete = layout_program_dtl.findViewById(R.id.btn_delete);

        // timer setting
        long total_time = count_down_time * 1000L;
        long hours = (total_time / 1000) / 3600;
        long minutes = ((total_time / 1000) % 3600) / 60;
        long seconds = (total_time / 1000) % 60;
        String formatted_time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);

        sport_name.setText(sports);
        text_timer.setText(formatted_time);
        program_playOrStop.setTag("ready");
        program_playOrStop.setImageResource(R.drawable.play);
        program_view.addView(layout_program_dtl);

        // add onclick listener
        program_playOrStop.setOnClickListener(new View.OnClickListener() {

            CountDownTimer timer;
            long remaining_time;
            @Override
            public void onClick(View v) {
                if (program_playOrStop.getTag() == "stop") {
                    // set timer with remaining time and start
                    timer = setTimer(layout_program_dtl, remaining_time + 999, 1000);
                    timer.start();

                    // change the image
                    program_playOrStop.setTag("start");
                    program_playOrStop.setImageResource(R.drawable.pause);
                }
                else if (program_playOrStop.getTag() == "start") {
                    // cancel timer and record remaining time
                    timer.cancel();
                    TextView remaining_time_text = layout_program_dtl.findViewById(R.id.timer);
                    remaining_time = formatTime(String.valueOf(remaining_time_text.getText()));

                    // change the image
                    program_playOrStop.setTag("stop");
                    program_playOrStop.setImageResource(R.drawable.play);
                }
                else {
                    // set timer and start
                    timer = setTimer(layout_program_dtl, total_time + 999, 1000);
                    timer.start();

                    // change the image
                    program_playOrStop.setTag("start");
                    program_playOrStop.setImageResource(R.drawable.pause);
                }
            }
        });
        program_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_programData(email, weekday, sports, count_down_time);
                program_view.removeView(layout_program_dtl);
            }
        });
        layout_program_dtl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // custom alert dialog
                AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(HomePageActivity.this);
                View custom_confirm_program = getLayoutInflater().inflate(R.layout.custom_confirm_dialog, null);
                confirmDialogBuilder.setView(custom_confirm_program);
                confirmDialog = confirmDialogBuilder.create();
                confirmDialog.show();


                // alert dialog elements and events
                text_sports = custom_confirm_program.findViewById(R.id.first_row);
                text_time = custom_confirm_program.findViewById(R.id.second_row);
                Button confirm = setBtnEvent(custom_confirm_program, R.id.btn_dialog_confirm, btn_dialog_confirm_update);
                Button cancel = setBtnEvent(custom_confirm_program, R.id.btn_dialog_cancel, btn_dialog_cancel);
                text_sports.setText(sports);
                text_time.setText(String.valueOf(count_down_time));

                // find unique ID in db
                Task<QuerySnapshot> result = search_programData(email, weekday, sports, count_down_time);
                result.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot result = task.getResult().getDocuments().get(0);
                            confirm.setTag(result.getId());
                        }
                    }
                });
                return true;
            }
        });
    }
    public CountDownTimer setTimer(@NonNull View view, long time, long countDownInterval) {
        return new CountDownTimer(time, countDownInterval) {
            final TextView text_timer = view.findViewById(R.id.timer);
            final ImageView program_playOrStop = view.findViewById(R.id.btn_timer_control);

            @Override
            public void onTick(long millisUntilFinished) {
                long hours = (millisUntilFinished / 1000) / 3600;
                long minutes = ((millisUntilFinished / 1000) % 3600) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String formatted_time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                text_timer.setText(formatted_time);
            }

            @Override
            public void onFinish() {
                text_timer.setText(R.string.time_0);
                program_playOrStop.setTag("ready");
                program_playOrStop.setImageResource(R.drawable.play);
                long hours = (time / 1000) / 3600;
                long minutes = ((time / 1000) % 3600) / 60;
                long seconds = (time / 1000) % 60;
                String formatted_time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                text_timer.setText(formatted_time);
            }
        };
    }

    /**
     *
     * @param string_time Unformatted time. ex. 00:00:00
     * @return formatted time(s)
     */
    public long formatTime(@NonNull String string_time) {
        String[] seq = string_time.split(":");
        int hours = Integer.parseInt(seq[0]);
        int minutes = Integer.parseInt(seq[1]);
        int seconds = Integer.parseInt(seq[2]);

        return ((hours * 3600L) + (minutes * 60L) + seconds) * 1000L;
    }
    public String getDate(int unit, int interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(unit, interval);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(calendar.getTime());
    }
    public int getDateNum(int interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, interval);
        return calendar.get(Calendar.DATE);
    }

    // Program common function
    /**
     * create program data into database
     * @param email FirebaseUser.getEmail()
     * @param weekday program_view.getTag()
     * @param custom_program Program object
     */
    public void create_programData(String email, String weekday, Program custom_program) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("program").document(email).collection(weekday).add(custom_program)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomePageActivity.this, "Failed to create into database", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Before using update_programData function, using search_programData function and add OnCompleteListener
     * to get the unique document ID
     *
     * @param email currentUser,getEmail()
     * @param weekday String.valueOf(program_view.getTag())
     * @param docID unique document ID
     * @param new_name String.valueOf(text_sports.getText())
     * @param new_time Integer.parseInt(String.valueOf(text_time.getText()))
     */
    public void update_programData(String email, String weekday, String docID, String new_name, int new_time) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("program").document(email).collection(weekday).document(docID)
                .update("name", new_name, "time", new_time);
    }

    /**
     * delete program data from db by giving current object
     *
     * @param email currentUser,getEmail()
     * @param weekday String.valueOf(program_view.getTag())
     * @param name sports
     * @param time count_down_time
     */
    public void delete_programData(String email, String weekday, String name, int time) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task<QuerySnapshot> result = search_programData(email, weekday, name, time);
        result.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                    DocumentSnapshot result = task.getResult().getDocuments().get(0);
                    String docID = result.getId();
                    db.collection("program").
                            document(email).collection(weekday).document(docID).delete();
                }
            }
        });
    }
    public Task<QuerySnapshot> search_programData(String email, String weekday, String name, int time) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("program").document(email).collection(weekday).
                whereEqualTo("name", name).whereEqualTo("time", time).get();
    }
    public void collect_currentDay_programData(String email, String weekday) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("program").document(email).collection(weekday).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot result : task.getResult()){
                                addIntoProgram(true, String.valueOf(result.get("name")), Integer.parseInt(String.valueOf(result.get("time"))));
                            }
                        }
                    }
                });
    }

    // Explore common function
    public void collect_exploreData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("explore").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot result: task.getResult()) {
                        addIntoExplore(true,
                                String.valueOf(result.get("userName")),
                                String.valueOf(result.get("uri")),
                                String.valueOf(result.get("description")),
                                String.valueOf(result.get("date")));
                    }
                }
            }
        });
    }
    public void delete_exploreData(String userName, String description, String date) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("explore").whereEqualTo("userName", userName)
                .whereEqualTo("description", description)
                .whereEqualTo("date", date).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot result = task.getResult().getDocuments().get(0);
                            String docID = result.getId();
                            db.collection("explore").document(docID).delete();
                        }
                    }
                });
    }
    public void addIntoExplore(Boolean load_from_db, String userName, String imageUri, String description, String date) {
        if (load_from_db) {
            // objects
            @SuppressLint("InflateParams") View explore_dtl = getLayoutInflater().inflate(R.layout.custom_explore_dtl, null);
            TextView text_username = explore_dtl.findViewById(R.id.explore_username);
            TextView text_desc = explore_dtl.findViewById(R.id.explore_description);
            TextView text_date = explore_dtl.findViewById(R.id.explore_date);
            ImageView image_heart = explore_dtl.findViewById(R.id.explore_heart);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference refer = storage.getReference().child(imageUri);

            // load image from web storage
            refer.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();

                        ImageView image = explore_dtl.findViewById(R.id.explore_image);
                        Picasso.get().load(uri).fit().into(image);
                    }
                }
            });

            // set into textview
            text_username.setText(userName);
            text_desc.setText(description);
            text_date.setText(date);
            explore_view.addView(explore_dtl);

            // heart onclick
            image_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (String.valueOf(image_heart.getTag()).equals("null") || TextUtils.isEmpty(String.valueOf(image_heart.getTag())) || String.valueOf(image_heart.getTag()).equals("empty")) {
                        image_heart.setImageResource(R.drawable.heart_full);
                        image_heart.setTag("full");
                    }
                    else {
                        image_heart.setImageResource(R.drawable.heart_empty);
                        image_heart.setTag("empty");
                    }
                }
            });
            explore_dtl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (Objects.equals(currentUser.getDisplayName(), userName)) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomePageActivity.this);
                        View custom_alert = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
                        alertDialog.setView(custom_alert);
                        AlertDialog dialog = alertDialog.create();
                        dialog.show();

                        Button btn_delete = custom_alert.findViewById(R.id.btn_confirm_logout);
                        Button btn_cancel = custom_alert.findViewById(R.id.btn_cancel);
                        TextView intro = custom_alert.findViewById(R.id.alert_dialog_intro);
                        intro.setText(getString(R.string.title_delete));
                        btn_delete.setText(getString(R.string.btn_delete));

                        btn_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                explore_view.removeView(explore_dtl);
                                delete_exploreData(userName, description, date);
                                dialog.dismiss();
                            }
                        });
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            });
        }
    }

    // diet common function
    public void collect_currentDay_dietData(String email, String date) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("diet").document(email).collection(date).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot result : task.getResult()) {
                                addIntoDiet(true, String.valueOf(result.get("name")), Integer.parseInt(String.valueOf(result.get("calories"))));
                            }
                        }
                    }
                });
    }
    public void addIntoDiet(Boolean load_from_db, String name, int calories) {
        String email = currentUser.getEmail();
        String date = String.valueOf(diet_view.getTag());
        if (!load_from_db) {
            Diet custom_diet = new Diet(name, calories);
            create_dietData(email, date, custom_diet);
        }

        // objects
        @SuppressLint("InflateParams") View layout_diet_dtl = getLayoutInflater().inflate(R.layout.custom_diet_dtl, null);
        TextView text_foodName = layout_diet_dtl.findViewById(R.id.text_foodname);
        TextView text_calories = layout_diet_dtl.findViewById(R.id.text_calories);
        ImageView diet_delete = layout_diet_dtl.findViewById(R.id.diet_dtl_delete);

        text_foodName.setText(name);
        text_calories.setText(String.valueOf(calories));
        diet_view.addView(layout_diet_dtl);
        calculate_total_calories(calories, "add");

        // events
        diet_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_dietData(email, date, name, calories);
                diet_view.removeView(layout_diet_dtl);
            }
        });
        layout_diet_dtl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // custom alert dialog
                AlertDialog.Builder dietUpdateDialogBuilder = new AlertDialog.Builder(HomePageActivity.this);
                View custom_diet_dialog = getLayoutInflater().inflate(R.layout.custom_diet_confirm_dialog, null);
                dietUpdateDialogBuilder.setView(custom_diet_dialog);
                confirmDialog = dietUpdateDialogBuilder.create();
                confirmDialog.show();

                // alert dialog elements and events
                foodName = custom_diet_dialog.findViewById(R.id.diet_first_row);
                calories_input = custom_diet_dialog.findViewById(R.id.diet_second_row);
                Button confirm = setBtnEvent(custom_diet_dialog, R.id.btn_diet_dialog_confirm, btn_diet_dialog_update);
                Button cancel = setBtnEvent(custom_diet_dialog, R.id.btn_diet_dialog_cancel, btn_dialog_cancel);
                foodName.setText(name);
                calories_input.setText(String.valueOf(calories));

                // find unique ID in db
                Task<QuerySnapshot> result = search_dietData(email, date, name, calories);
                result.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot result = task.getResult().getDocuments().get(0);
                            confirm.setTag(result.getId());
                        }
                    }
                });
                return true;
            }
        });

    }
    public void create_dietData(String email, String date, Diet custom_diet) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("diet").document(email).collection(date).add(custom_diet)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomePageActivity.this, "Failed to create into database", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void delete_dietData(String email, String date, String name, int calories) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task<QuerySnapshot> result = search_dietData(email, date, name, calories);
        result.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                    calculate_total_calories(calories, "minus");
                    DocumentSnapshot result = task.getResult().getDocuments().get(0);
                    String docID = result.getId();
                    db.collection("diet").
                            document(email).collection(date).document(docID).delete();
                }
            }
        });
    }
    public void update_dietData(String email, String date, String docID, String new_name, int new_calories) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("diet").document(email).collection(date).document(docID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            int origin_calories = Integer.parseInt(String.valueOf(task.getResult().get("calories")));
                            calculate_total_calories(new_calories - origin_calories, "add");
                        }
                    }
                });

        // update
        db.collection("diet").document(email).collection(date).document(docID)
                .update("name", new_name, "calories", new_calories);
    }
    public Task<QuerySnapshot> search_dietData(String email, String date, String name, int calories) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("diet").document(email).collection(date).
                whereEqualTo("name", name).whereEqualTo("calories", calories).get();
    }
    public void calculate_total_calories(int calories, @NonNull String oper) {
        int current_total = Integer.parseInt(String.valueOf(total_calories.getText()));
        int total;
        if (oper.equals("add")) {
            total = current_total + calories;
        }
        else {
            total = current_total - calories;
        }
        total_calories.setText(String.valueOf(total));
    }
}