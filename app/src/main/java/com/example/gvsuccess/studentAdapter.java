package com.example.gvsuccess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class studentAdapter extends RecyclerView.Adapter<studentAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private LayoutInflater layoutInflater;
    private List<ScheduledSession> data;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    studentAdapter(Context context, List<ScheduledSession> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.student_recycle_view, parent, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // bind the textview with data received
        ScheduledSession session = data.get(position);
        String studentEmail = session.getStudentEmail();
        Long startTime = session.getStartTime();

        holder.textEmail.setText(studentEmail);
        holder.textStart.setText(startTime.toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textEmail, textStart;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textEmail = itemView.findViewById(R.id.textEmail);
            textStart = itemView.findViewById(R.id.textStart);

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
