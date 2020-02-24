package com.example.gvsuccess;

public class Tutor {
    private String tutorID;
    private String firstName;
    private String lastName;
    private String email;
    private String password; // TODO: Is password needed here?
    private String successCenterCode;
    private boolean available;

    public Tutor(){};

    public Tutor(String tutorID, String firstName, String lastName,
                 String email, String password, String successCenterCode,
                 boolean available) {
        this.tutorID = tutorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.successCenterCode = successCenterCode;
        this.available = available;
    }

    public String getTutorID() {
        return tutorID;
    }

    public void setTutorID(String tutorID) {
        this.tutorID = tutorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSuccessCenterCode() {
        return successCenterCode;
    }

    public void setSuccessCenterCode(String successCenterCode) {
        this.successCenterCode = successCenterCode;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
