package com.example.gvsuccess;

public class Student {
    private String firstName;
    private String lastName;
    private String email;

    private String successCenterCode;


    public Student() {};



    public Student( String fName, String lName,
                   String email,
                   String successCenterCode) {

        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
        this.successCenterCode = successCenterCode;


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


    public String getSuccessCenterCode() {
        return successCenterCode;
    }

    public void setSuccessCenterCode(String successCenterCode) {
        this.successCenterCode = successCenterCode;
    }


}
