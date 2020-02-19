package com.example.gvsuccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import com.example.gvsuccess.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    private Button chem, cis, math, stat;

    final private String TAG = "MAIN ACTIVITY";

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button command to access chemistry page
        chem = findViewById(R.id.chemB);
        chem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openChem();
            }
        });

        //Button command to access CIS page
        cis = findViewById(R.id.cisB);
        cis.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openCIS();
            }
        });

        //Button command to access Math page
        math = findViewById(R.id.mathB);
        math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMath();
            }
        });

        //Button command to access Stats page
        stat = findViewById(R.id.statB);
        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStats();
            }
        });

        db.collection("success centers")
        .get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());

        /* The output of this log is
        D/MAIN ACTIVITY: chemistry => {open flag=true, address=399 PAD, success center code=GVSU-CHM, field=chemistry, num available tutors=5}
         math => {open flag=true, address=MAK A-2-601, success center code=GVSU-MTH, field=mathematics, num available tutors=3} */

                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }

    //Open Chemistry activity
    public void openChem(){
        Intent intent = new Intent(this, chemPage.class);
        startActivity(intent);
    }

    //Open CIS activity
    public void openCIS(){
        Intent intent = new Intent(this, cisPage.class);
        startActivity(intent);
    }

    //Open Math activity
    public void openMath(){
        Intent intent = new Intent(this, mathPage.class);
        startActivity(intent);
    }

    //Opne Stats Activity
    public void openStats(){
        Intent intent = new Intent(this, statsPage.class);
        startActivity(intent);
    }
}
