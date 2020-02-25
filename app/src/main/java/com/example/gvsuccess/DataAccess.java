package com.example.gvsuccess;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class DataAccess {
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void addStudent(Student student) {
        db.collection("students").document(student.getStudentID()).set(student);

    }

    public void deleteStudent(Student student) {
        db.collection("students").document(student.getStudentID())
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
        db.collection("tutors").document(tut.getTutorID()).set(tut);
    }

    public void deleteTutor(Tutor tut) {
        db.collection("tutors").document(tut.getTutorID())
                .delete()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("delTutorFailure", "Tutor was not deleted" );
                    }
                });
    }

    public void addSession(ScheduledSession session) {
        db.collection("scheduled session").document(session.getStudentID()).set(session);
    }

    public void deleteSession(ScheduledSession session) {
        db.collection("scheduled session").document(session.getStudentID())
                .delete()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("delSessionFailure", "Session was not deleted" );
                    }
                });
    }
}

