package com.example.gvsuccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gvsuccess.R;

public class MainActivity extends AppCompatActivity {
    private Button chem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chem = findViewById(R.id.chemB);
        chem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openChem();
            }
        });
    }

    public void openChem(){
        Intent intent = new Intent(this, chemPage.class);
        startActivity(intent);
    }
}
