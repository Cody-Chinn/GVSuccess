package com.example.gvsuccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.*;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class schedulingPage extends AppCompatActivity {
    private DataAccess data = new DataAccess();
    private Scheduler sched;
    private List<String> classes = new ArrayList<>();
    private ArrayList<ScheduledSession> sessions = new ArrayList<>();
    private Button submitBtn;

    TimePicker picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_scheduling_page);

        //Get name of center clicked
        Intent i = getIntent();
        SuccessCenter successCenter = (SuccessCenter)i.getSerializableExtra("successCenter");

        //Set title for center clicked
        TextView tv = findViewById(R.id.centerTitle);
        tv.setText(successCenter.getTitle());

        Task<QuerySnapshot> courses = data.getCourse(successCenter);
        courses.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            public void onSuccess(QuerySnapshot snapshot) {
                for (QueryDocumentSnapshot doc : snapshot) {
                    if(doc.exists()) {
                        CoursesModel course = doc.toObject(CoursesModel.class);
                        classes.add(course.getCourseID());
                    }
                }
                classes.add("Other");
                fillSpinner();

            }
        });

        picker = (TimePicker)findViewById(R.id.timePicker);
        //picker.setIs24HourView(true);

        submitBtn = (Button)findViewById(R.id.submitB);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Task<QuerySnapshot> sess = data.getSessions();


                sess.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    public void onSuccess(QuerySnapshot snapshot) {
                        int hour, minute;
                        sessions = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : snapshot) {
                            if(doc.exists()) {
                                ScheduledSession sesh = doc.toObject(ScheduledSession.class);
                                sessions.add(sesh);

                            }
                        }
                        sched = new Scheduler(sessions);
                        // Check if we need to use the deprecated
                        // getCurrentHour/minute functions depending on
                        // build version
                        if(Build.VERSION.SDK_INT >= 23) {
                            hour = picker.getHour();
                            minute = picker.getMinute();
                        } else {
                            hour = picker.getCurrentHour();
                            minute = picker.getCurrentMinute();
                        }

                        Intent i = getIntent();
                        SuccessCenter successCenter = (SuccessCenter)i.getSerializableExtra("successCenter");

                        long time = hour*100 + minute;
                        boolean scheduled = sched.scheduleSession(successCenter, "124", "1234", "03:09:2020", time, 15);

                        if(scheduled == false) {
                            Log.v("sched", "Scheduling failed.");
                        }

                    }
                });





            }
        });


    }

    private void fillSpinner() {
        Spinner classSpinner = findViewById(R.id.classSelection);
        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.schedule_spinner, classes);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String item = parent.getItemAtPosition(position).toString();
                Log.v("spin", item);
                Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }
}
