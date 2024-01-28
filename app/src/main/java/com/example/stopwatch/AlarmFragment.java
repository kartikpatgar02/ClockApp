package com.example.stopwatch;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class AlarmFragment extends Fragment {
    private TimePicker timePicker;
    private Button setAlarmButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        timePicker = view.findViewById(R.id.timePicker);
        setAlarmButton = view.findViewById(R.id.setAlarmButton);

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
                Toast.makeText(getContext(),"Your Alarm is Set", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    private void setAlarm() {
        int hour, minute;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            // For Android versions M (23) and above
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            // For Android versions below M (23)
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(requireContext(), AlarmReceiver.class);
        intent.setAction("com.example.stopwatch.ALARM_TRIGGER");

        // Specify FLAG_IMMUTABLE or FLAG_MUTABLE based on your requirements
        int flags = PendingIntent.FLAG_MUTABLE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, flags);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


        // Specify FLAG_IMMUTABLE or FLAG_MUTABLE based on your requirements
        flags = 0; // Use 0 if not sure, or choose FLAG_IMMUTABLE or FLAG_MUTABLE
        pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, flags | PendingIntent.FLAG_IMMUTABLE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
    }

