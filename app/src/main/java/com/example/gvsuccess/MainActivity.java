package com.example.gvsuccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.*;
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
        DataAccess da = new DataAccess();


        Task<QuerySnapshot> centers = da.getCenters();

        centers.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {


            public void onSuccess(QuerySnapshot snapshot) {
                for (QueryDocumentSnapshot doc : snapshot) {
                    if(doc.exists()) {
                        SuccessCenter cent = doc.toObject(SuccessCenter.class);
                        cent.setKey(doc.getId());
                        items.add(cent);
                    }
                }
                createCardViews();
            }
        });


    }

    public void createCardViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new Adapter(context, items);
        recyclerView.setAdapter(adapter);
        setupCardViewClickListeners();
    }

    private void setupCardViewClickListeners() {

        final Intent intent = new Intent(this, schedulingPage.class);
        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SuccessCenter successCenter = items.get(position);
                intent.putExtra("successCenter", successCenter);
                startActivity(intent);
            }
        });
    }
}
