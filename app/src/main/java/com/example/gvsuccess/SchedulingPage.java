package com.example.gvsuccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchedulingPage extends AppCompatActivity {

    final private String TAG = "Scheduling Page";
    private List<String> classes = new ArrayList<>();
    private Button mCheckInBtn;
    private String mSelectedClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduling_page);

        mCheckInBtn = findViewById(R.id.checkInB);

        //Get name of center clicked
        Intent i = getIntent();
        final SuccessCenter successCenter = (SuccessCenter)i.getSerializableExtra("successCenter");
        final Student student = (Student)i.getSerializableExtra("student");

        //Set title for center clicked
        TextView tv = findViewById(R.id.centerTitle);
        tv.setText(successCenter.getTitle());

        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("success centers").document(successCenter.getKey())
                .collection("coursesOffered")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            CoursesModel course = document.toObject(CoursesModel.class);

                           classes.add(course.getCourseID());
                        }
                    }
                    classes.add("Other");
                    fillSpinner();
                } else {
                    // get documents failed
                }
            }
        });

        mCheckInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStudentIn(successCenter, student);
            }
        });
    }

    private void checkStudentIn(SuccessCenter successCenter, Student student){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference studentsInLine = db.collection("/success centers/" + successCenter.getKey() + "/students_in_line/");

        Timestamp timestamp = Timestamp.now();

        Map<String, Object> docData = new HashMap<>();
        docData.put("studentID", student.getStudentID());
        docData.put("firstName", student.getFirstName());
        docData.put("lastName", student.getLastName());
        docData.put("email", student.getEmail());
        docData.put("checkInTime", timestamp);
        docData.put("className", mSelectedClass);

        studentsInLine.add(docData);
        Toast.makeText(this, "Your appointment has been scheduled!", Toast.LENGTH_SHORT).show();
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
                mSelectedClass = item;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }
}
