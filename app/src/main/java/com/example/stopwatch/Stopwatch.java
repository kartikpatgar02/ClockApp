package com.example.stopwatch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.MessageFormat;
import java.util.Locale;

public class Stopwatch extends Fragment {
    private TextView stopwatchTextView;
    private Button startButton, stopButton, resetButton;
    private boolean running;
    private long startTime, elapsedTime;
    private Handler handler = new Handler();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);

        stopwatchTextView = view.findViewById(R.id.stopwatchTextView);
        startButton = view.findViewById(R.id.startButton);
        stopButton = view.findViewById(R.id.stopButton);
        resetButton = view.findViewById(R.id.resetButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopwatch(v);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStopwatch(v);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetStopwatch(v);
            }
        });
        return view;
    }

    public void startStopwatch(View view) {
        if (!running) {
            startTime = SystemClock.elapsedRealtime() - elapsedTime;
            handler.postDelayed(updateStopwatch, 0);
            running = true;
        }
    }

    public void stopStopwatch(View view) {
        if (running) {
            handler.removeCallbacks(updateStopwatch);
            elapsedTime = SystemClock.elapsedRealtime() - startTime;
            running = false;
        }
    }

    public void resetStopwatch(View view) {
        elapsedTime = 0;
        stopwatchTextView.setText("00:00:00");
    }

    private Runnable updateStopwatch = new Runnable() {
        @Override
        public void run() {
            long currentTime = SystemClock.elapsedRealtime() - startTime;
            long updatedTime = elapsedTime + currentTime;
            int seconds = (int) (updatedTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (updatedTime % 1000) / 10;

            stopwatchTextView.setText(String.format("%02d:%02d:%02d", minutes, seconds, milliseconds));
            handler.postDelayed(this, 10); // Update every 10 milliseconds
        }
    };
}
//just for test112

