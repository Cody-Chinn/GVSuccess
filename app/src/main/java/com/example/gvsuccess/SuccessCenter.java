package com.example.gvsuccess;

import java.io.Serializable;
import java.util.ArrayList;

public class SuccessCenter implements Serializable {
    private String address;
    private String description;
    private int numAvailableTutors;
    private boolean open;
    private String successCenterCode;
    private String title;
    private String key;
    private ArrayList<Student> studentsInLine;

    public SuccessCenter(){

    }

    public SuccessCenter(String address, String description, int numAvailableTutors, boolean open, String successCenterCode, String title, String key) {
        this.address = address;
        this.description = description;
        this.numAvailableTutors = numAvailableTutors;
        this.open = open;
        this.successCenterCode = successCenterCode;
        this.title = title;
        this.key = key;
        this.studentsInLine  = new ArrayList<>();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumAvailableTutors() {
        return numAvailableTutors;
    }

    public void setNumAvailableTutors(int numAvailableTutors) {
        this.numAvailableTutors = numAvailableTutors;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getSuccessCenterCode() {
        return successCenterCode;
    }

    public void setSuccessCenterCode(String successCenterCode) {
        this.successCenterCode = successCenterCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() { return key; }

    public void setKey(String key) { this.key = key; }

    public int getLineLength(){
        return studentsInLine.size();
    }

    public void addStudentToLine(Student student){
        studentsInLine.add(student);
    }

    public void removeStudentFromLine(Student student){
        studentsInLine.remove(student);
    }
}
