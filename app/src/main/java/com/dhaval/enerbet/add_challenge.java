package com.dhaval.enerbet;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.Calendar;

public class add_challenge extends AppCompatActivity {
    DatabaseReference mDatabase;
    ChallengesClass challengesClass;
    SeekBar seekBar;
    CheckBox checkBox_20,checkBox_50,checkBox_100;
    Button button_add,button_enddate,button_startdate;
    int bet = 50;
    TextView textView_betamount;
    static TextView textView_startdate;
    static TextView textView_enddate;
    static int which_button = 0;
    static String startdate, start_time;
    static String enddate,end_time;
    static int hour;
    static int minute;
    static int second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);

        final EditText editText_name = findViewById(R.id.input_name);
        final EditText editText_description = findViewById(R.id.input_description);
        textView_betamount = findViewById(R.id.bet_value);
        textView_startdate = findViewById(R.id.textview_startdate);
        textView_enddate = findViewById(R.id.textview_enddate);
        seekBar = findViewById(R.id.seekbar);
        checkBox_20 = findViewById(R.id.checkbox_20);
        checkBox_50 = findViewById(R.id.checkbox_50);
        checkBox_100 = findViewById(R.id.checkbox_100);
        button_add = findViewById(R.id.button_add);
        button_startdate = findViewById(R.id.start_date_picker);
        button_enddate = findViewById(R.id.end_date_picker);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    textView_betamount.setText("Bet amount: $" + progress);
                    checkBox_20.setChecked(false);
                    checkBox_50.setChecked(false);
                    checkBox_100.setChecked(false);
                    bet = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        checkBox_20.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    checkBox_50.setChecked(false);
                    checkBox_100.setChecked(false);
                    seekBar.setProgress(20);
                    textView_betamount.setText("Bet amount: $20");
                    bet = 10;
                }
            }
        });
        checkBox_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    checkBox_20.setChecked(false);
                    checkBox_100.setChecked(false);
                    seekBar.setProgress(50);
                    textView_betamount.setText("Bet amount: $50");
                    bet = 50;
                }
            }
        });
        checkBox_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    checkBox_20.setChecked(false);
                    checkBox_50.setChecked(false);
                    seekBar.setProgress(100);
                    textView_betamount.setText("Bet amount: $100");
                    bet = 100;
                }
            }
        });

        button_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which_button = 0;
                DialogFragment newFragment = new DatePickerFragment();
               newFragment.show(getSupportFragmentManager(), "datePicker");
            timepick();

            }
        });
        button_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which_button = 1;
               DialogFragment newFragment = new DatePickerFragment();
               newFragment.show(getSupportFragmentManager(), "datePicker");
                timepick();
            }
        });
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        ChallengesClass challengesClass = new ChallengesClass();
                        challengesClass.setName(editText_name.getText().toString());
                challengesClass.setDescription(editText_description.getText().toString());
                challengesClass.setBet(bet);
                challengesClass.setPlayers(0);
                challengesClass.setStart_date("" + startdate + start_time);
                challengesClass.setEnd_date("" + enddate + end_time);
                mDatabase.child("challenges").child(editText_name.getText().toString()).setValue(challengesClass);
                finish();
            }
        });


    }

void timepick(){
    Calendar mcurrentTime = Calendar.getInstance();
    final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
    final int minute = mcurrentTime.get(Calendar.MINUTE);
    TimePickerDialog mTimePicker;
    final DecimalFormat mFormat= new DecimalFormat("00");

    mTimePicker = new TimePickerDialog(add_challenge.this, new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
           String h = ""+ (selectedHour<10?("0"+selectedHour):(selectedHour));
            String m = ""+ (selectedMinute<10?("0"+selectedMinute):(selectedMinute));
            if(which_button == 0){

                start_time = " " + h + ":" + m + ":" + "00";
                textView_startdate.append(" " + h + ":" + m + ":" + "00");
            }
            if(which_button == 1){
                end_time = " " + h + ":" + m + ":" + "00";
                textView_enddate.append(" " + h + ":" + m + ":" + "00");
            }
        }
    }, hour, minute, true);//Yes 24 hour time
    mTimePicker.setTitle("Select Time");
    mTimePicker.show();
}
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
             hour = c.get(Calendar.HOUR);
             minute = c.get(Calendar.MINUTE);
             second = c.get(Calendar.SECOND);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            month = month + 1;
            String m = ""+ (month<10?("0"+month):(month));
            String d = ""+ (day<10?("0"+day):(day));
            if(which_button == 0){
                startdate = "" + m + "-" + d + "-" + year;
                textView_startdate.setText("Start Date:" + m + "/" + d + "/" + year);
            }
            if(which_button == 1){
                enddate = "" + m + "-" + d + "-" + year ;
                textView_enddate.setText("Start Date:" + m + "/" + d + "/" + year);
            }
        }



    }
}
