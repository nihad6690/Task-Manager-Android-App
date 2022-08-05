package com.example.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    public tasks_recyclerViewAdapter adapter;
    public static final int TEXT_REQUEST  = 1;
    public Button editButton;
    public static final String EXTRA_TITLE = "com.example.todolist.EXTRA_TITLE";
    ConstraintLayout conLayout;
    public tasksViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.recTitle);
        textViewDescription = itemView.findViewById(R.id.recDescription);
        itemView.findViewById(R.id.delBtn).setOnClickListener(view -> {
            adapter.tasks.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition());
            Toast.makeText(((Activity)view.getContext()).getApplicationContext(),"Deleted the task",Toast.LENGTH_SHORT).show();
        });
        itemView.findViewById(R.id.editBtn).setOnClickListener(view -> {
            Intent output = new Intent(view.getContext(), editTask.class);
            output.putExtra("title", adapter.tasks.get(getAdapterPosition()).getTitle());
            output.putExtra("description", adapter.tasks.get(getAdapterPosition()).getDescription());
            output.putExtra("pos", getAdapterPosition());
            Applicationclass applicationclass = (Applicationclass)((Activity)view.getContext()).getApplication();
            applicationclass.setAdapter(adapter);
            ((Activity)view.getContext()).startActivityForResult(output, 2);

        });
    }






    public tasksViewHolder linkAdapter(tasks_recyclerViewAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}