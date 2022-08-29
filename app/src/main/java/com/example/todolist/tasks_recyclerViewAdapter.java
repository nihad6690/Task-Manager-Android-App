package com.example.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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
        holder.textViewDescription.setText(tasks.get(position).getShortenedDescription());

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
        itemView.findViewById(R.id.threeDots).setOnClickListener(view ->{
            FloatingActionButton delete_btn, edit_btn, eye_btn;
            delete_btn = itemView.findViewById(R.id.delBtn);
            edit_btn = itemView.findViewById(R.id.editBtn);
            eye_btn = itemView.findViewById(R.id.eye);
            Animation rotateOpenAnim, rotateCloseAnim, fromBottomAnim, toTopAnim;
            rotateOpenAnim = AnimationUtils.loadAnimation(view.getContext(),R.anim.rotate_open_anim);
            rotateCloseAnim = AnimationUtils.loadAnimation(view.getContext(),R.anim.rotate_close_anim);
            fromBottomAnim = AnimationUtils.loadAnimation(view.getContext(),R.anim.from_bottom_anim);
            toTopAnim = AnimationUtils.loadAnimation(view.getContext(),R.anim.to_top_anim);
            boolean clicked = false;

            if(!clicked){
                edit_btn.setVisibility(View.VISIBLE);
                delete_btn.setVisibility(View.VISIBLE);
                eye_btn.setVisibility(View.VISIBLE);
                edit_btn.startAnimation(fromBottomAnim);
                eye_btn.startAnimation(fromBottomAnim);
                delete_btn.startAnimation(fromBottomAnim);
                itemView.findViewById(R.id.threeDots).startAnimation(rotateOpenAnim);
                itemView.findViewById(R.id.threeDots).setOnClickListener(V ->{
                    edit_btn.setVisibility(V.INVISIBLE);
                    delete_btn.setVisibility(V.INVISIBLE);
                    eye_btn.setVisibility(V.INVISIBLE);
                    edit_btn.startAnimation(toTopAnim);
                    eye_btn.startAnimation(toTopAnim);
                    delete_btn.startAnimation(toTopAnim);
                    itemView.findViewById(R.id.threeDots).startAnimation(rotateCloseAnim);
                });
                edit_btn.setVisibility(View.VISIBLE);
                delete_btn.setVisibility(View.VISIBLE);
                eye_btn.setVisibility(View.VISIBLE);
            }


            /*
            if(clicked){
                edit_btn.startAnimation(fromBottomAnim);
                eye_btn.startAnimation(fromBottomAnim);
                delete_btn.startAnimation(fromBottomAnim);
                itemView.findViewById(R.id.threeDots).startAnimation(rotateOpenAnim);
                edit_btn.setClickable(false);
                eye_btn.setClickable(false);
                delete_btn.setClickable(false);
                clicked = false;
            }
            else{
                edit_btn.startAnimation(toTopAnim);
                eye_btn.startAnimation(toTopAnim);
                delete_btn.startAnimation(toTopAnim);
                itemView.findViewById(R.id.threeDots).startAnimation(rotateCloseAnim);
                edit_btn.setClickable(true);
                eye_btn.setClickable(true);
                delete_btn.setClickable(true);
                clicked = true;
            }
            */



        });
        itemView.findViewById(R.id.delBtn).setOnClickListener(view -> {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.getReference().child("tasks").child(adapter.tasks.get(getAdapterPosition()).getUserId()).child(adapter.tasks.get(getAdapterPosition()).getTaskId()).setValue(null);
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

