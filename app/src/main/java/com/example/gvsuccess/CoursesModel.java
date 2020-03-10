package com.example.gvsuccess;

public class CoursesModel {

    private String courseID;

    private CoursesModel(){

    }

    public CoursesModel(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }
}
