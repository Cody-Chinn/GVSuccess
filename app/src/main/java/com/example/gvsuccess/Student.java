package com.example.gvsuccess;

public class Student {
    private String studentID;
    private String firstName;
    private String lastName;
    private String email;
    private String password; // TODO: Do we really need password in this class?
    private String successCenterCode;
    private boolean available;

    public Student(String ID, String fName, String lName,
                   String email, String password,
                   String successCenterCode, boolean available) {

        this.studentID = ID;
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
        this.password = password;
        this.successCenterCode = successCenterCode;
        this.available = available;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
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
