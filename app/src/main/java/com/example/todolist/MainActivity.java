package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button addBtn;
    public static final int TEXT_REQUEST  = 1;
    public TextView setTitle;
    private TextView setDescription;
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
        setTitle = findViewById(R.id.newTitle);
        setDescription = findViewById(R.id.description);
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
                this.setTitle.setText(title);
                this.setTitle.setVisibility(View.VISIBLE);
                this.setDescription.setText(description);
                this.setDescription.setVisibility(View.VISIBLE);
        }
    }


}