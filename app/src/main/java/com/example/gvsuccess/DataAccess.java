package com.example.gvsuccess;


import com.google.firebase.firestore.FirebaseFirestore;


public class DataAccess {
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void addStudent(Student student) {
        db.collection("students").document(student.getStudentID() + "").set(student);

    }

    public void addSuccessCenter(SuccessCenter center) {
        db.collection("success centers").document(center.getTitle() + "").set(center);

    }

    public void addTutor(Tutor tut) {
        db.collection("tutors").document(tut.getTutorID() + "").set(tut);
    }

    public void addSession(ScheduledSession session) {
        db.collection("scheduled session").document(session.getStudentID() + "").set(session);
    }
}

