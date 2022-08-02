package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class tasks_recyclerViewAdapter extends RecyclerView.Adapter<tasksViewHolder> {
    Context context;
    ArrayList<information> tasks;


    public tasks_recyclerViewAdapter(Context context, ArrayList<information> tasks){
        this.context = context;
        this.tasks = tasks;

    }

    @NonNull
    @Override
    public tasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new tasksViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull tasksViewHolder holder, int position) {
        holder.textViewTitle.setText(tasks.get(position).getTitle());
        holder.textViewDescription.setText(tasks.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


}

class tasksViewHolder extends RecyclerView.ViewHolder{

    TextView textViewTitle;
    TextView textViewDescription;
    private tasks_recyclerViewAdapter adapter;

    public tasksViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.recTitle);
        textViewDescription = itemView.findViewById(R.id.recDescription);
        itemView.findViewById(R.id.delBtn).setOnClickListener(view -> {
            adapter.tasks.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition());
        });
    }

    public tasksViewHolder linkAdapter(tasks_recyclerViewAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}