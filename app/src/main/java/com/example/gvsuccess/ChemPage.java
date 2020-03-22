package com.example.gvsuccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChemPage extends AppCompatActivity {
    private Button scheduleB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chem_page);

        //Button setup
        scheduleB = findViewById(R.id.schedule);
        scheduleB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSchedule();
            }
        });
    }

    public void openSchedule() {
        Intent intent = new Intent(this, SchedulingPage.class);
        startActivity(intent);
    }
}
