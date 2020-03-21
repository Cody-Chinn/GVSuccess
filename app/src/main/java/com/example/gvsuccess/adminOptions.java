package com.example.gvsuccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminOptions extends AppCompatActivity {
    private Button getHelpB, asTutorB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);

        getHelpB = findViewById(R.id.gettingHelpButton);
        asTutorB = findViewById(R.id.asTutorButton);

        getHelpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelpCenters();
            }
        });
        asTutorB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTutorCenters();
            }
        });
    }

    public void openHelpCenters(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openTutorCenters(){
        //Intent intent = new Intent(this, AdminCenterSelect.class);
        //startActivity(intent);
    }
}
