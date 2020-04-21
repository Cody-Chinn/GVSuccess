package com.example.gvsuccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class AppointmentConfirmation extends AppCompatActivity {

    private final String TAG = "ApptConfirmation";
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_confirmation);

        Bundle extras = getIntent().getExtras();
        TextView tv_msg = findViewById(R.id.tv_confirm_msg);

        try {
            tv_msg.setText(extras.getString("confirmation_msg"));
        }catch(Exception e){
            Log.e(TAG, "Problem getting confirmation message, setting a generic message");
            tv_msg.setText("You are now in line");
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(AppointmentConfirmation.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }


}
