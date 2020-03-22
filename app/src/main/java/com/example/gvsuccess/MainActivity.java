package com.example.gvsuccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final private String TAG ="Main Activity";
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<SuccessCenter> items;
    private Context context;

    private String mGoogleUsername;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        items = new ArrayList<>();
        context = this;

        try {
            // Get the users account that is logged in and set the title
            Bundle mExtras = getIntent().getExtras();
            mGoogleUsername = mExtras.getString("account");

            setTitle("Welcome, " + mGoogleUsername);

        } catch(Exception e) {
            Log.e(TAG, e.toString());
        }

        db.collection("success centers")
        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            SuccessCenter successCenter = document.toObject(SuccessCenter.class);
                            successCenter.setKey(document.getId());
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

    public void createCardViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new Adapter(context, items);
        recyclerView.setAdapter(adapter);
        setupCardViewClickListeners();
    }

    private void setupCardViewClickListeners() {
        final Intent intent = new Intent(this, SchedulingPage.class);

        // This will need to be taken out and replaced with getting the current signed in user
        final Student student = new Student("G01234456","Foo", "Bar", "fbar@mail.gvsu.edu");

        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SuccessCenter successCenter = items.get(position);
                intent.putExtra("successCenter", successCenter);
                intent.putExtra("student", student);
                startActivity(intent);
            }
        });
    }
}
