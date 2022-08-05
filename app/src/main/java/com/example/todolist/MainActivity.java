package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button addBtn;
    public static final int TEXT_REQUEST  = 1;


    ArrayList<information> tasks = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                addTaskActivity();
            }
        });
        recyclerView = findViewById(R.id.mRecylerView);


    }


    public void addTaskActivity(){
        Intent intent = new Intent(this, addTask.class);
        startActivityForResult(intent, TEXT_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST){
                String title = data.getStringExtra(addTask.EXTRA_TITLE);
                String description = data.getStringExtra(addTask.EXTRA_Description);
                if (title != null && description != null){
                    tasks.add(new information(title, description));
                    tasks_recyclerViewAdapter adapter = new tasks_recyclerViewAdapter(this, tasks);
                    this.recyclerView.setAdapter(adapter);
                    this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                }

        }
        if(resultCode == 2){
            String title = data.getStringExtra(addTask.EXTRA_TITLE);
            String description = data.getStringExtra(addTask.EXTRA_Description);
            Integer pos = data.getIntExtra("pos",0);
            Applicationclass applicationclass = (Applicationclass) this.getApplication();
            tasks_recyclerViewAdapter adapter = applicationclass.getAdapter();
            adapter.tasks.get(pos).setTitle(title);
            adapter.tasks.get(pos).setDescription(description);
            adapter.notifyItemChanged(pos);
            Toast.makeText(getApplicationContext(),"Updated the task",Toast.LENGTH_SHORT).show();

        }
    }


}