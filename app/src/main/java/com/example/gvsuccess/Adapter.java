package com.example.gvsuccess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // bind the textview with data received
        SuccessCenter successCenter = data.get(position);
        String numTutors = successCenter.getNumAvailableTutors() + " Tutors Avalable";
        String open = successCenter.isOpen()? "Open": "Closed";


        holder.textTitle.setText(successCenter.getTitle());
        holder.textLocation.setText((successCenter.getAddress()));
        holder.textNumTutors.setText(numTutors);
        holder.textDescr.setText(successCenter.getDescription());
        holder.textOpen.setText(open);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textLocation, textNumTutors, textDescr, textOpen;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textLocation = itemView.findViewById(R.id.textLocation);
            textNumTutors = itemView.findViewById(R.id.textNumTutors);
            textDescr = itemView.findViewById(R.id.textDescr);
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
