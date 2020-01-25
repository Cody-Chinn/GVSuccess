package com.example.feasibility;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    Button greetings, farewell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        greetings = (Button)findViewById(R.id.greetings);
        farewell = (Button)findViewById(R.id.farewell);

        greetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView)findViewById(R.id.screenText)).setText("Hello World");
            }
        });

        farewell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ((TextView)findViewById(R.id.screenText)).setText("Goodbye Forever");
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://dummy.restapiexample.com/api/v1/employees";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        ((TextView)findViewById(R.id.restView)).setText("Response: " + response.toString());
                        Log.e("Rest Response ", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Rest Error", error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }
}
