package com.example.feasibility;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button greetings, farewell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        greetings = (Button)findViewById(R.id.greetings);
        farewell = (Button)findViewById(R.id.farewell);

        greetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView)findViewById(R.id.screenText)).setText("Hello World");
            }
        });

        farewell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ((TextView)findViewById(R.id.screenText)).setText("Goodbye Forever");
            }
        });
    }
}
