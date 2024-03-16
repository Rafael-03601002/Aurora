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

import java.util.Calendar;
import java.util.Locale;

public class HomePageActivity extends AppCompatActivity {
    // Common object
    public FirebaseAuth mAuth;
    public FirebaseUser currentUser;
    public int seq;

    // home page (base) objects
    public Button mart, program, explore;
    private ConstraintLayout base_view;

    // custom_program objects
    private View layout_program, layout_mart, layout_explore;
    private LinearLayout program_view;
    public Button monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    // confirm_program_dialog objects
    private TextInputEditText text_sports, text_time;
    private AlertDialog programDialog;

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
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

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
            AlertDialog.Builder programDialogBuilder = new AlertDialog.Builder(HomePageActivity.this);
            View custom_confirm_program = getLayoutInflater().inflate(R.layout.custom_confirm_program_dialog, null);
            programDialogBuilder.setView(custom_confirm_program);
            programDialog = programDialogBuilder.create();
            programDialog.show();

            // alert dialog elements and events
            text_sports = custom_confirm_program.findViewById(R.id.sports);
            text_time = custom_confirm_program.findViewById(R.id.text_timer);
            Button confirm = setBtnEvent(custom_confirm_program, R.id.btn_dialog_confirm, btn_dialog_confirm_add);
            Button cancel = setBtnEvent(custom_confirm_program, R.id.btn_dialog_cancel, btn_dialog_cancel);
        }
    };
    public View.OnClickListener btn_dialog_confirm_add = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String sports_name = String.valueOf(text_sports.getText());
            int timer = Integer.parseInt(String.valueOf(text_time.getText()));

            addIntoProgram(false, sports_name, timer);
            programDialog.dismiss();    // same as dialog.cancel()
        }
    };
    public View.OnClickListener btn_dialog_cancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            programDialog.cancel();
        }
    };
    public View.OnClickListener btn_dialog_confirm_update = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = currentUser.getEmail();
            String weekday = String.valueOf(program_view.getTag());
            String sports_name = String.valueOf(text_sports.getText());
            int timer = Integer.parseInt(String.valueOf(text_time.getText()));
            String docID = String.valueOf(v.getTag());

            update_programData(email, weekday, docID, sports_name, timer);
            program_view.removeAllViews();
            collect_currentDay_programData(email, weekday);
            programDialog.dismiss();
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
        @SuppressLint("InflateParams") View layout_program_dtl = getLayoutInflater().inflate(R.layout.program, null);
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
                AlertDialog.Builder programDialogBuilder = new AlertDialog.Builder(HomePageActivity.this);
                View custom_confirm_program = getLayoutInflater().inflate(R.layout.custom_confirm_program_dialog, null);
                programDialogBuilder.setView(custom_confirm_program);
                programDialog = programDialogBuilder.create();
                programDialog.show();


                // alert dialog elements and events
                text_sports = custom_confirm_program.findViewById(R.id.sports);
                text_time = custom_confirm_program.findViewById(R.id.text_timer);
                text_sports.setText(sports);
                text_time.setText(String.valueOf(count_down_time));
                Button confirm = setBtnEvent(custom_confirm_program, R.id.btn_dialog_confirm, btn_dialog_confirm_update);
                Button cancel = setBtnEvent(custom_confirm_program, R.id.btn_dialog_cancel, btn_dialog_cancel);

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

    /**
     *
     * @param email FirebaseUser.getEmail()
     * @param weekday program_view.getTag()
     * @param program Program object
     */
    public void create_programData(String email, String weekday, Program program) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("program").document(email).collection(weekday).add(program)
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
}