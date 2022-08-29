package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class viewTask extends AppCompatActivity {
    private EditText editTitle;
    private EditText editDescription;

    private ImageView backArrow;
    public static final String EXTRA_TITLE = "com.example.todolist.EXTRA_TITLE";
    public static final String EXTRA_Description = "com.example.todolist.EXTRA_Description";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(viewTask.this, R.color.white));
        Intent intent = getIntent();
        setContentView(R.layout.activity_view_task);
        editTitle = findViewById(R.id.editTextTitle);
        editDescription = findViewById(R.id.editTextDescription);
        editTitle.setText(intent.getStringExtra("title"));
        editDescription.setText(intent.getStringExtra("description"));
        Integer pos = intent.getIntExtra("pos", 0);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent output = new Intent();
                setResult(RESULT_CANCELED, output);
                finish();
            }
        });
    }
}