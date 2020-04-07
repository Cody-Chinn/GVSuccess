package com.example.gvsuccess;

import com.google.firebase.Timestamp;

import java.util.Date;

public class ScheduledSession {
    private String tutorID;
    private String studentEmail;
    private String successCenterCode;
    private Timestamp date;
    public long startTime; // Measured in milliseconds from midnight
    private long estimatedSessionLength;

    public ScheduledSession(){};

    public ScheduledSession(String tutorID, String studentEmail,
                            String successCenterCode, Timestamp date,
                            long startTime, long estimatedSessionLength) {
        this.tutorID = tutorID;
        this.studentEmail = studentEmail;
        this.successCenterCode = successCenterCode;
        this.date = date;
        this.startTime = startTime;
        this.estimatedSessionLength = estimatedSessionLength;
    }

    public String getTutorID() {
        return tutorID;
    }

    public void setTutorID(String tutorID) {
        this.tutorID = tutorID;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getSuccessCenterCode() {
        return successCenterCode;
    }

    public void setSuccessCenterCode(String successCenterCode) {
        this.successCenterCode = successCenterCode;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEstimatedSessionLength() {
        return estimatedSessionLength;
    }

    public void setEstimatedSessionLength(long estimatedSessionLength) {
        this.estimatedSessionLength = estimatedSessionLength;
    }
}
