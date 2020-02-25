package com.example.gvsuccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gvsuccess.R;

public class MainActivity extends AppCompatActivity {
    private Button chem, cis, math, stat;

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

        //Button command to access Math page
        math = findViewById(R.id.mathB);
        math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMath();
            }
        });

        //Button command to access Stats page
        stat = findViewById(R.id.statB);
        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStats();
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

    //Open Math activity
    public void openMath(){
        Intent intent = new Intent(this, mathPage.class);
        startActivity(intent);
    }

    //Opne Stats Activity
    public void openStats(){
        Intent intent = new Intent(this, statsPage.class);
        startActivity(intent);
    }
}
