package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class addTask extends AppCompatActivity {
    private EditText editTitle;
    private EditText editDescription;
    private Button submitBtn;
    public static final String EXTRA_TITLE = "com.example.todolist.EXTRA_TITLE";
    public static final String EXTRA_Description = "com.example.todolist.EXTRA_Description";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        editTitle = findViewById(R.id.editTextTitle);
        editDescription = findViewById(R.id.editTextDescription);
        submitBtn = (Button) findViewById(R.id.submitbtn);
        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){

                String title = editTitle.getText().toString();
                String description = editDescription.getText().toString();
                Intent output = new Intent();
                output.putExtra(EXTRA_TITLE, title);
                output.putExtra(EXTRA_Description, description);
                setResult(RESULT_OK, output);
                finish();
            }
        });
    }
}