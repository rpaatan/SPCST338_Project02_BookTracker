package com.example.book_tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private ArrayList<String> stringsListTemp;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String title);
    }

    public RecyclerAdapter(ArrayList<String> stringsListTemp, OnItemClickListener listener) {
        this.stringsListTemp = stringsListTemp;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText;

        public MyViewHolder(final View view) {
            super(view);
            titleText = view.findViewById(R.id.recyclerTemplateText);
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.recycler_template, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String title = stringsListTemp.get(position);
        holder.titleText.setText(title);

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(title);
        });
    }

    @Override
    public int getItemCount() {
        return stringsListTemp.size();
    }
}
