package com.example.gvsuccess;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class DataAccess {
    FirebaseFirestore db;
    public ArrayList<Tutor> tutors;
    Handler handler;
    public DataAccess() {
        db = FirebaseFirestore.getInstance();
        handler = new Handler(Looper.getMainLooper());
        tutors = new ArrayList<>();

    }
    public void addStudent(Student student) {
        db.collection("students").document(student.getEmail()).set(student);

    }

    public void deleteStudent(Student student) {
        db.collection("students").document(student.getEmail())
                .delete()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("delStudFailure", "Student was not deleted" );
                    }
                });
    }

    public void addSuccessCenter(SuccessCenter center) {
        db.collection("success centers").document(center.getTitle()).set(center);

    }

    public void deleteSuccessCenter(SuccessCenter center) {
        db.collection("success centers").document(center.getTitle())
                .delete()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("delCenterFailure", "Center was not deleted" );
                    }
                });
    }
    public void addTutor(Tutor tut) {
        db.collection("tutors").document(tut.getEmail()).set(tut);
    }

    public void deleteTutor(Tutor tut) {
        db.collection("tutors").document(tut.getEmail())
                .delete()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("delTutorFailure", "Tutor was not deleted" );
                    }
                });
    }

    public void addSession(ScheduledSession session) {
        String seshID = session.getStudentEmail() + ":"
                + session.getTutorID() + ":"
                + session.getDate() + ":"
                + session.getStartTime();
        db.collection("scheduled session").document(seshID).set(session);
    }

    public void deleteSession(ScheduledSession session) {
        String seshID = session.getStudentEmail() + ":"
                + session.getTutorID() + ":"
                + session.getDate() + ":"
                + session.getStartTime();
        db.collection("scheduled session").document(seshID)
                .delete()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("delSessionFailure", "Session was not deleted" );
                    }
                });
    }

    public Task<QuerySnapshot> getTutors() {

        Task<QuerySnapshot> task = db.collection("tutors").get();
        return task;

        // The below snippet is an example of how to parse through
        // the returned task for this function in the calling class.
        /*
        task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            public void onSuccess(QuerySnapshot snapshot) {

                for (QueryDocumentSnapshot doc : snapshot) {
                    if(doc.exists()) {
                        Tutor tutor = doc.toObject(Tutor.class);

                        //Note that tutors must be an instance variable
                        tutors.add(tutor);
                    }
                }

            }
        });
        */

    }

    public Task<QuerySnapshot> getCenters() {
        Task<QuerySnapshot> task = db.collection("success centers").get();
        return task;
    }

    public Task<QuerySnapshot> getStudents() {
        Task<QuerySnapshot> task = db.collection("students").get();
        return task;
    }

    public Task<DocumentSnapshot> getStudent(String studentEmail) {
        Task<DocumentSnapshot> task = db.collection("students")
                .document(studentEmail).get();
                //.whereEqualTo("email", studentEmail).get();

        return task;
    }

    public Task<QuerySnapshot> getSessions() {
        Task<QuerySnapshot> task = db.collection("scheduled session").get();
        return task;
    }

    public Task<QuerySnapshot> getCourse(SuccessCenter center) {
        Task<QuerySnapshot> task = db.collection("success centers")
                .document(center.getKey())
                .collection("coursesOffered")
                .get();
        return task;
    }

    public void updateAvailable(String email, boolean flag) {
        db.collection("tutors").document(email).update("available", flag);
    }


    public Task<QuerySnapshot> getCheckedIn(String successCenterCode) {
        Calendar cal = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(cal.getTime());
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int nextHour = hour + 1;
        int minute = cal.get(Calendar.MINUTE) - 15;
        hour = hour * 100 + minute;
        nextHour = nextHour * 100 + minute;
        Task<QuerySnapshot> task = db.collection("scheduled session")
                .whereEqualTo("successCenterCode", successCenterCode)
                .whereEqualTo("date", currentDate)
                .whereGreaterThanOrEqualTo("startTime", hour)
                .whereLessThanOrEqualTo("startTime", nextHour)
                .get();
        return task;
    }

    public Task<DocumentSnapshot> getHours(String dayOfWeek, SuccessCenter successCenter) {
        Task<DocumentSnapshot> task = db.collection("success centers")
                .document(successCenter.getKey())
                .collection("hours")
                .document(dayOfWeek)
                .get();
        return task;
    }
}

