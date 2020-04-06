package com.example.gvsuccess;

public class Tutor {
    private String firstName;
    private String lastName;
    private String email;
    private String successCenterCode;
    private boolean available;
    private String key;

    public Tutor(){};

    public Tutor(String firstName, String lastName,
                 String email, String successCenterCode,
                 boolean available, String key) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.successCenterCode = successCenterCode;
        this.available = available;
        this.key = key;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setKey(String key) { this.key = key; }

    public String getKey() { return key; }
}
