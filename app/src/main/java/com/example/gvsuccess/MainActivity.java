package com.example.gvsuccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gvsuccess.R;

public class MainActivity extends AppCompatActivity {
    private Button chem;
    private Button cis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button command to access chemistry page
        chem = findViewById(R.id.chemB);
        chem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openChem();
            }
        });

        //Button command to access CIS page
        cis = findViewById(R.id.cisB);
        cis.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openCIS();
            }
        });
    }

    //Open Chemistry activity
    public void openChem(){
        Intent intent = new Intent(this, chemPage.class);
        startActivity(intent);
    }

    //Open CIS activity
    public void openCIS(){
        Intent intent = new Intent(this, cisPage.class);
        startActivity(intent);
    }
}
