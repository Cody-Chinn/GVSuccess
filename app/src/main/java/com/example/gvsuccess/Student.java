package com.example.gvsuccess;

public class Student {
    private String firstName;
    private String lastName;
    private String email;



    public Student() {};



    public Student( String fName, String lName,
                   String email) {

        this.firstName = fName;
        this.lastName = lName;
        this.email = email;


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
