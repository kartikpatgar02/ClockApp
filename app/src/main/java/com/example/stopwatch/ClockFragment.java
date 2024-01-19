package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClockFragment extends Fragment {

    private TextView digitalClock;
    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock, container, false);
        digitalClock = view.findViewById(R.id.digitalClock);

        // Create a handler to update the time every second
        handler = new Handler();
        updateDigitalClock();

        return view;
    }

    private void updateDigitalClock() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Get the current time
                long currentTime = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
                String formattedTime = sdf.format(new Date(currentTime));

                // Update the TextView with the current time
                digitalClock.setText(formattedTime);

                // Schedule the next update after 1000ms (1 second)
                handler.postDelayed(this, 1000);
            }
        }, 1000); // Initial delay of 1 second
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove the callbacks to prevent memory leaks
        handler.removeCallbacksAndMessages(null);
    }
}
