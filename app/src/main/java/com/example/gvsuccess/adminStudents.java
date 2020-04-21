package com.example.gvsuccess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.WriteResult;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adminStudents extends AppCompatActivity {
    private Button signout;
    private DataAccess da;
    private RecyclerView rv;
    private studentAdapter adapter;
    private ArrayList<ScheduledSession> sessionList;
    private ArrayList<Student> studentList;
    private Context context;
    private SuccessCenter center;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_students);

        rv = findViewById(R.id.studentList);
        sessionList = new ArrayList<>();
        studentList = new ArrayList<>();
        context = this;
        da = new DataAccess();

        signout = findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStudentActivity();
            }
        });

        center = (SuccessCenter) getIntent().getExtras().get("successCenter");
        email = (String) getIntent().getExtras().get("email");

        Task<QuerySnapshot> sessions = da.getSessions();
        sessions.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshot) {
                for(QueryDocumentSnapshot snap : snapshot) {
                    if(snap.exists() && snap.getString("successCenterCode").equals(center.getSuccessCenterCode())) {
                        ScheduledSession s = snap.toObject(ScheduledSession.class);
                        s.setStudentEmail(snap.getString("studentEmail"));
                        s.setDate(snap.getString("date"));
                        s.setStartTime(snap.getLong("startTime"));
                        s.setSuccessCenterCode(snap.getString("successCenterCode"));
                        s.setTutorID(snap.getString("tutorID"));
                        sessionList.add(s);
                    }
                }
                createCardView();
            }
        });
    }

    private void createCardView() {
        rv.setLayoutManager(new LinearLayoutManager(context));
        adapter = new studentAdapter(context, sessionList);
        rv.setAdapter(adapter);
        setupCardViewListeners();
    }

    private void setupCardViewListeners() {
        final Intent intent = new Intent(this, adminStudents.class);

        adapter.setOnItemClickListener(new studentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                final ScheduledSession s = sessionList.get(position);
                Task<QuerySnapshot> session = da.getSessions();
                session.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            if(snapshot.getString("date").equals(s.getDate()) &&
                                    snapshot.getLong("startTime") == s.getStartTime() &&
                                    snapshot.getString("studentEmail").equals(s.getStudentEmail()) &&
                                    snapshot.getString("successCenterCode").equals(s.getSuccessCenterCode())) {
                                String key = s.getStudentEmail() + ":" + s.getTutorID() + ":" + s.getDate() + ":" + s.getStartTime();
                                DocumentReference dr = db.collection("scheduled session").document(key);
                                Map<String, Object> updates = new HashMap<>();
                                updates.put("date", FieldValue.delete());
                                updates.put("estimatedSessionLength", FieldValue.delete());
                                updates.put("startTime", FieldValue.delete());
                                updates.put("studentEmail", FieldValue.delete());
                                updates.put("successCenterCode", FieldValue.delete());
                                updates.put("tutorID", FieldValue.delete());
                                dr.update(updates);
                                dr.delete();
                            }
                        }
                        intent.putExtra("successCenter", center);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void openStudentActivity() {
        Intent intent = new Intent(this, adminCenters.class);
        da.decrementAvailable(email, center.getKey());

        intent.putExtra("successCenter", center);
        intent.putExtra("email", email);

        startActivity(intent);
    }
}
