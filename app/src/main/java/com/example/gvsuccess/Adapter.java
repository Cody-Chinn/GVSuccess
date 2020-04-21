package com.example.gvsuccess;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private LayoutInflater layoutInflater;
    private List<SuccessCenter> data;
    private String TAG = "Adapter";
    private DataAccess da;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    Adapter(Context context, List<SuccessCenter> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
        this.da = new DataAccess();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_recycler_view, parent, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        // bind the textview with data received
        final SuccessCenter successCenter = data.get(position);

        holder.textTitle.setText(successCenter.getTitle());
        holder.textLocation.setText((successCenter.getAddress()));

        setWaitTime(holder, successCenter);
        setHours(holder, successCenter);
    }

    private void setHours(@NonNull final ViewHolder holder, SuccessCenter successCenter) {
        Calendar currentDate = new GregorianCalendar();
        String dayOfWeek = currentDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()).toLowerCase();
        Task<DocumentSnapshot> hours = da.getHours(dayOfWeek, successCenter);
        hours.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Calendar cal = Calendar.getInstance();
                int currentHour = cal.get(Calendar.HOUR_OF_DAY);
                int currentMinute = cal.get(Calendar.MINUTE);
                int currentTime = currentHour * 100 + currentMinute;

                if (documentSnapshot.exists()) {
                    long openTime = (long) documentSnapshot.get("openTime");
                    long closeTime = (long) documentSnapshot.get("closeTime");
                    if (currentTime >= openTime && currentTime <= closeTime) {
                        holder.textOpen.setText("Open");
                        holder.textOpen.setTextColor(Color.GREEN);
                    } else {
                        holder.textOpen.setText("Closed");
                        holder.textOpen.setTextColor(Color.RED);
                    }
                    String openTimeString = getStandardTime((int) openTime);
                    String closeTimeString = getStandardTime((int) closeTime);

                    String hours = "Hours: " + openTimeString + " - " + closeTimeString;
                    holder.textHours.setText(hours);
                }
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.toString());
            }
        });
    }

    private void setWaitTime(@NonNull final ViewHolder holder, final SuccessCenter successCenter) {
        Task<QuerySnapshot> schedSessions = da.getCheckedIn(successCenter.getSuccessCenterCode());
        schedSessions.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            public void onSuccess(QuerySnapshot snapshot) {
                for (QueryDocumentSnapshot doc : snapshot) {
                    if(doc.exists()) {
                        ScheduledSession schedSession = doc.toObject(ScheduledSession.class);
                        Log.d(TAG, schedSession.getStudentEmail());
                    }
                }
                int numSessions = snapshot.size();
                int waitTime = numSessions * 15 / successCenter.getNumAvailableTutors();
                int hours = waitTime / 60;
                int minutes = waitTime % 60;
                String time = Integer.toString(minutes) + " minutes";
                if (hours > 0) {
                    time = Integer.toString(hours) + " hours and " + time;
                }
                String waitTimeString = "Estimated Wait Time: " + time;
                holder.textWaitTime.setText(waitTimeString);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.toString());
            }
        });
    }

    private String getStandardTime(int num) {
        int hour = num / 100;
        int minute = (int) num % 100;
        String minuteString = Integer.toString(minute);
        String amPm = " am";

        if (minute < 10) {
            minuteString = Integer.toString(minute) + "0";
        }
        if (hour > 12) {
            hour = hour - 12;
            amPm = " pm";
        } else if (hour == 0) {
            hour = 1;
        }

        String standardTime = Integer.toString(hour) + ":" + minuteString + amPm;
        return standardTime;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textLocation, textHours, textWaitTime, textOpen;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textLocation = itemView.findViewById(R.id.textLocation);
            textHours = itemView.findViewById(R.id.textHours);
            textWaitTime = itemView.findViewById(R.id.textWaitTime);
            textOpen = itemView.findViewById(R.id.textOpen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick((position));
                        }
                    }
                }
            });
        }
    }
}
