package com.example.gvsuccess;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createStudent();
        //getStudents();
    }

    private void getStudents(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://4xix8cgigk.execute-api.us-east-2.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AwsApi awsApi = retrofit.create(AwsApi.class);
        Call<Student> call = awsApi.getStudents();

        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (!response.isSuccessful()) {
                    Log.e("awsapi", "Retrieval Error: " + response.code());
                    return;
                }
                Student s = response.body();

                Log.i("awsapi", "StudentID: " + s.getStudentID());
                Log.i("awsapi", "FirstName: " + s.getFirstName());
                Log.i("awsapi", "LastName: " + s.getLastName());
                Log.i("awsapi", "Email: " + s.getEmail());
                Log.i("awsapi", "Password: " + s.getPassword());

            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Log.e("awsapi", t.getMessage());
            }
        });
    }

    private void createStudent(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://4xix8cgigk.execute-api.us-east-2.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AwsApi awsApi = retrofit.create(AwsApi.class);
        Student student = new Student("G01248108", "Jay", "Brunsting", "test@gmail.com", "pass");

        Call<Student> call = awsApi.createPost(student);

        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (!response.isSuccessful()) {
                    Log.e("awsapi", "Retrieval Error: " + response.code());
                    return;
                }
                Student s = response.body();

                Log.i("aswapi", "Code: " + response.code());
                Log.i("awsapi", "StudentID: " + s.getStudentID());
                Log.i("awsapi", "FirstName: " + s.getFirstName());
                Log.i("awsapi", "LastName: " + s.getLastName());
                Log.i("awsapi", "Email: " + s.getEmail());
                Log.i("awsapi", "Password: " + s.getPassword());

            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Log.e("awsapi", t.getMessage());
            }
        });
    }
}
