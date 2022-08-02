package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class tasks_recyclerViewAdapter extends RecyclerView.Adapter<tasks_recyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<information> tasks;


    public tasks_recyclerViewAdapter(Context context, ArrayList<information> tasks){
        this.context = context;
        this.tasks = tasks;

    }

    @NonNull
    @Override
    public tasks_recyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new tasks_recyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tasks_recyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textViewTitle.setText(tasks.get(position).getTitle());
        holder.textViewDescription.setText(tasks.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;
        TextView textViewDescription;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.recTitle);
            textViewDescription = itemView.findViewById(R.id.recDescription);
        }
    }
}
