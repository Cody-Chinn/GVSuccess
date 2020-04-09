package com.example.gvsuccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminStudents extends AppCompatActivity {
    private Button signout;
    DataAccess da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_students);

        da = new DataAccess();

        signout = findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStudentActivity();
            }
        });
    }

    private void openStudentActivity() {
        Intent intent = new Intent(this, adminCenters.class);

        SuccessCenter center = (SuccessCenter) getIntent().getExtras().get("successCenter");
        String email = getIntent().getExtras().getString("email");
        da.decrementAvailable(email, center.getKey());

        intent.putExtra("successCenter", center);
        intent.putExtra("email", email);

        startActivity(intent);
    }
}
