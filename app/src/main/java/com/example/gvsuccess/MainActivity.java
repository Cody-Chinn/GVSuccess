package com.example.gvsuccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<SuccessCenter> items;
    private Context context;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        items = new ArrayList<>();
        context = this;

        db.collection("success centers")
        .get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            SuccessCenter successCenter = document.toObject(SuccessCenter.class);
                            items.add(successCenter);
                        }
                    }

                    createCardViews();
                } else {
                    // get documents failed
                }
            }
        });
    }

    private void createCardViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new Adapter(context, items);
        recyclerView.setAdapter(adapter);
        setupCardViewClickListeners();
    }

    private void setupCardViewClickListeners() {
        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SuccessCenter successCenter = items.get(position);
                switch (successCenter.getSuccessCenterCode()) {
                    case "GVSU-CHM":
                        openChem();
                        break;
                    case "GVSU-MTH":
                        openMath();
                        break;
                    case "GVSU-CIS":
                        openCIS();
                        break;
                    case "GVSU-STA":
                        openStats();
                        break;
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
