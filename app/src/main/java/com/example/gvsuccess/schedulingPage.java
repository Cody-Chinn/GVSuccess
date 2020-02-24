package com.example.gvsuccess;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class schedulingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduling_page);

        //Drop down center selection
        Spinner centerSpinner = findViewById(R.id.centerSelection);
        //Drop down class selection
        //Spinner classSpinner = findViewById(R.id.classSelection);

        //Center spinner options
        List<String> centers = new ArrayList<>();
        centers.add("Select a Center");
        centers.add("Chemistry");
        centers.add("CIS");
        centers.add("Math");
        centers.add("Statistics");

        //Class spinner options
        //List<String> classes = new ArrayList<>();
        //classes.add("Other");

        //Adapter for spinner
        ArrayAdapter<String> centerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.schedule_spinner, centers);
        centerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        centerSpinner.setAdapter(centerAdapter);

        //ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.schedule_spinner, classes);
        //classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //classSpinner.setAdapter(classAdapter);

        //On click listeners
        centerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();

                List<String> classes = new ArrayList<>();
                if(item.equals("Chemistry")){
                    classes.clear();
                    classes.add("CHM 115");
                    classes.add("CHM 116");
                }
                else if(item.equals("CIS")){
                    classes.clear();
                    classes.add("CIS 162");
                    classes.add("CIS 163");
                }
                else if(item.equals("Math")){
                    classes.clear();
                    classes.add("MTH 201");
                    classes.add("MTH 202");
                }
                else if(item.equals("Statistics")){
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
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

//        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
//                String item = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {}
//        });
    }
}
