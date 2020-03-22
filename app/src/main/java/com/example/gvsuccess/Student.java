package com.example.gvsuccess;

import java.io.Serializable;

public class Student implements Serializable {
    private String studentID;
    private String firstName;
    private String lastName;
    private String email;

    public Student() {};

    public Student(String ID, String fName, String lName,
                   String email) {

        this.studentID = ID;
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
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
}
