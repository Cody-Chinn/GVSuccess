package com.example.gvsuccess;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AwsApi {

    @GET("dev/student")
    Call<Student> getStudents();

    @POST("dev/success-center")
    Call<Student> createPost(@Body Student student);
}
