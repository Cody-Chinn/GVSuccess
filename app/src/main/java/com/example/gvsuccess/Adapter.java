package com.example.gvsuccess;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private LayoutInflater layoutInflater;
    private List<SuccessCenter> data;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    Adapter(Context context, List<SuccessCenter> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
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
        String openHours = "<ADD HOURS HERE FROM DB>";
        String open = successCenter.isOpen()? "Open": "Closed";


        holder.textTitle.setText(successCenter.getTitle());
        holder.textLocation.setText((successCenter.getAddress()));
        holder.textHours.setText(openHours);
        holder.textOpen.setText(open);

        DataAccess da = new DataAccess();
        Task<QuerySnapshot> schedSessions = da.getCheckedIn(successCenter.getSuccessCenterCode());
        schedSessions.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            public void onSuccess(QuerySnapshot snapshot) {
                for (QueryDocumentSnapshot doc : snapshot) {
                    if(doc.exists()) {
                        ScheduledSession schedSession = doc.toObject(ScheduledSession.class);
                        Log.d("ScheduledSession", schedSession.getStudentEmail());
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
                Log.d("Query Failed", e.toString());
            }
        });
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
