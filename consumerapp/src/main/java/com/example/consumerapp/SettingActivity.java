package com.example.consumerapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.consumerapp.alarm.AlarmReceiver;

import java.util.Objects;

public class SettingActivity extends AppCompatActivity {
    private AlarmReceiver alarmReceiver;
    Switch dailyswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alarmReceiver = new AlarmReceiver();
        dailyswitch = findViewById(R.id.switchDaily);
        SharedPreferences sharedPreferences = getSharedPreferences("checked",MODE_PRIVATE);
        dailyswitch.setChecked(sharedPreferences.getBoolean("value",false));
        dailyswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences.Editor editor = getSharedPreferences("checked",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    alarmReceiver.setDailyReminder(SettingActivity.this,alarmReceiver.TYPE_DAILY,"09:00",getString(R.string.daily_message));
                }else{
                    SharedPreferences.Editor editor = getSharedPreferences("checked",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    alarmReceiver.cancelAlarm(SettingActivity.this);
                }
            }
        });
    }
}