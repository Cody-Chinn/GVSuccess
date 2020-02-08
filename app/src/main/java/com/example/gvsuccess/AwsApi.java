package com.example.gvsuccess;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AwsApi {

    @GET("dev/student")
    Call<Student> getStudents();
}
