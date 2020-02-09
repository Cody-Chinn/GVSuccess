package com.example.gvsuccess;

public class Student {

    private String studentID;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    public Student(String studentID, String firstName, String lastName, String email, String password) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
