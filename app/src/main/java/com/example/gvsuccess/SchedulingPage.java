package com.example.gvsuccess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TimePicker;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.*;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchedulingPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private DataAccess data = new DataAccess();
    private Scheduler sched;
    private List<String> classes = new ArrayList<>();
    private List<String> tutorNames;
    private List<Tutor> tutors;
    private Tutor selectedTutor;
    private ArrayList<ScheduledSession> sessions = new ArrayList<>();
    private Intent i;
    private SuccessCenter successCenter;
    private Student student;
    TimePicker picker;
    private Button displayDate, submitBtn, checkInBtn;
    private String mSelectedClass, userEmail;
    private Date selectedDate;
    private Timestamp stamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar cal = Calendar.getInstance();
        selectedDate = cal.getTime();

        stamp = new Timestamp(selectedDate);

        String currentDate = DateFormat.getDateInstance().format(cal.getTime());

        tutorNames = new ArrayList<>();
        tutors = new ArrayList<>();

        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        userEmail = acc.getEmail();
        setContentView(R.layout.activity_scheduling_page);

        //Get name of center clicked
        i = getIntent();
        successCenter = (SuccessCenter)i.getSerializableExtra("successCenter");
        student = (Student)i.getSerializableExtra("student");

        //Set title for center clicked
        TextView tv = findViewById(R.id.centerTitle);
        tv.setText(successCenter.getTitle());

        checkInBtn = findViewById(R.id.checkInB);

        checkInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStudentIn(successCenter, student);
            }
        });

        displayDate = findViewById(R.id.btnDate);
        displayDate.setText(currentDate);

        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

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

        Task<QuerySnapshot> tut = data.getTutors();
        tut.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            public void onSuccess(QuerySnapshot snapshot) {
                for (QueryDocumentSnapshot doc : snapshot) {
                    if(doc.exists()) {
                        Tutor tutor = doc.toObject(Tutor.class);
                        if(successCenter.getSuccessCenterCode().equals(tutor.getSuccessCenterCode())) {
                            String tutorName = tutor.getLastName() + ", " + tutor.getFirstName();
                            tutorNames.add(tutorName);
                            tutors.add(tutor);
                        }

                    }
                }
                fillSpinner();

            }
        });

        picker = (TimePicker)findViewById(R.id.timePicker);

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
                                Map data = doc.getData();
                                // ScheduledSession sesh = doc.toObject(ScheduledSession.class);
                                String tutorID = data.get("tutorID").toString();
                                String studentEmail = data.get("studentEmail").toString();
                                String successCenterCode = data.get("successCenterCode").toString();
                                String dateStr = data.get("date").toString();

                                Date date = convertStrToDate(dateStr);
                                Timestamp stamp = new Timestamp(date);

                                long startTime = Long.parseLong(data.get("startTime").toString());


                                ScheduledSession sesh = new ScheduledSession(tutorID, studentEmail, successCenterCode, stamp, startTime, 15);

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
                        boolean scheduled = sched.scheduleSession(successCenter, userEmail, selectedTutor.getEmail(), stamp, time, 15);

                        if(!scheduled) {
                            Log.v("sched", "Scheduling failed.");
                        }

                    }
                });
            }
        });
    }

    public void checkStudentIn(SuccessCenter successCenter, Student student){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference studentsInLine = db.collection("/success centers/" + successCenter.getKey() + "/students_in_line/");

        Timestamp timestamp = Timestamp.now();

        Map<String, Object> docData = new HashMap<>();
        docData.put("firstName", student.getFirstName());
        docData.put("lastName", student.getLastName());
        docData.put("email", student.getEmail());
        docData.put("checkInTime", timestamp);
        docData.put("tutor", selectedTutor.getFirstName() + " " + selectedTutor.getLastName());
        docData.put("className", mSelectedClass);

        studentsInLine.add(docData);
        Toast.makeText(this, "Your appointment has been scheduled!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        selectedDate = c.getTime();
        String date = DateFormat.getDateInstance().format(c.getTime());

        displayDate.setText(date);
    }

    private void fillSpinner() {
        Spinner classSpinner = findViewById(R.id.classSelection);
        Spinner tutorSpinner = findViewById(R.id.tutorSelection);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.schedule_spinner, classes);
        ArrayAdapter<String> tutorAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.schedule_spinner, tutorNames);

        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tutorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        classSpinner.setAdapter(classAdapter);
        tutorSpinner.setAdapter(tutorAdapter);

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String item = parent.getItemAtPosition(position).toString();
                mSelectedClass = item;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        tutorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String item = parent.getItemAtPosition(position).toString();
                selectedTutor = tutors.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    private Date convertStrToDate(String dateStr){
        String[] tokens = dateStr.split(" ");
        String converter = "";
        Date date;

        if(Integer.parseInt(tokens[1].replace(",","")) < 10){
            converter += "0";
        }

        converter += tokens[1].replace(",", "");
        converter += "/";

        switch(tokens[0]){
            case "Jan":
                converter += "01/";
                break;
            case "Feb":
                converter += "02/";
                break;
            case "Mar":
                converter += "03/";
                break;
            case "Apr":
                converter += "04/";
                break;
            case "May":
                converter += "05/";
                break;
            case "Jun":
                converter += "06/";
                break;
            case "Jul":
                converter += "07/";
                break;
            case "Aug":
                converter += "08/";
                break;
            case "Sep":
                converter += "09/";
                break;
            case "Oct":
                converter += "10/";
                break;
            case "Nov":
                converter += "11/";
                break;
            case "Dec":
                converter += "12/";
                break;
            default:
                return null;
        }

        converter += tokens[2];
        Log.i("convertStrToDate", converter);

        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(converter);
            return date;
        }catch (ParseException e){
            Log.e("Str to Date err: ", e.toString());
        }

        return null;
    }
}
