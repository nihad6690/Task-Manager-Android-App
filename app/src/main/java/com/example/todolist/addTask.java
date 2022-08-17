package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class addTask extends AppCompatActivity {
    private EditText editTitle;
    private EditText editDescription;
    private FloatingActionButton submitBtn;
    private Button cancelBtn;
    private ImageView backArrow;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    public static final String EXTRA_TITLE = "com.example.todolist.EXTRA_TITLE";
    public static final String EXTRA_Description = "com.example.todolist.EXTRA_Description";
    public static final String EXTRA_KEY = "key";
    public static final String EXTRA_USERID = "user id";


    public static String generateRandomKey() {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                +"lmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 30; i++){
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }

        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(addTask.this, R.color.white));
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        setContentView(R.layout.activity_add_task);
        editTitle = findViewById(R.id.editTextTitle);
        editDescription = findViewById(R.id.editTextDescription);
        submitBtn = (FloatingActionButton) findViewById(R.id.submitBtn);
        backArrow = findViewById(R.id.backArrow);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){

                String title = editTitle.getText().toString();
                String description = editDescription.getText().toString();
                String key = generateRandomKey();
                Intent output = new Intent();
                output.putExtra(EXTRA_TITLE, title);
                output.putExtra(EXTRA_Description, description);
                output.putExtra(EXTRA_KEY, key);
                output.putExtra(EXTRA_USERID, id);

                information newTask = new information(title.toString(), description.toString());

                firebaseDatabase.getReference().child("tasks").child(id).child(key).setValue(newTask);
                setResult(RESULT_OK, output);
                finish();
            }
        });
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