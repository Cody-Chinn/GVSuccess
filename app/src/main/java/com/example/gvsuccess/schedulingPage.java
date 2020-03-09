package com.example.gvsuccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class schedulingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduling_page);

        //Get name of center clicked
        Intent i = getIntent();
        String centerClicked = i.getStringExtra("centerName");

        //Set title for center clicked
        TextView tv = findViewById(R.id.centerTitle);
        tv.setText(centerClicked);

        //On click listeners
        List<String> classes = new ArrayList<>();
        if(centerClicked.equals("Chemistry Center")){
            classes.clear();
            classes.add("CHM 115");
            classes.add("CHM 116");
        }
        else if(centerClicked.equals("CIS Center")){
            classes.clear();
            classes.add("CIS 162");
            classes.add("CIS 163");
        }
        else if(centerClicked.equals("Math Center")){
            classes.clear();
            classes.add("MTH 201");
            classes.add("MTH 202");
        }
        else if(centerClicked.equals("Statistics Center")){
            classes.clear();
            classes.add("STA 215");
        }
        else
            classes.clear();
        classes.add("Other");

        Spinner classSpinner = findViewById(R.id.classSelection);
        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.schedule_spinner, classes);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
