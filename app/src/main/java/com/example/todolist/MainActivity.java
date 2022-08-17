package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private Button addBtn;
    private TextView todaysDate;
    public static final int TEXT_REQUEST  = 1;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    public String id;


    ArrayList<information> tasks = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView = findViewById(R.id.mRecylerView);
        Applicationclass applicationclass = (Applicationclass)(MainActivity.this.getApplication());
        boolean databaseContent = applicationclass.isNotUsedDatabaseContent();
        final FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            id = mFirebaseUser.getUid();
        }
        todaysDate = findViewById(R.id.date);
        Date currentDate = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy");
        todaysDate.setText(timeFormat.format(currentDate).toString());





        addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                addTaskActivity(id);
            }
        });

        Log.d("myTag", "This is my message");
        firebaseDatabase.getReference().child("tasks").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot currentSnapshot: snapshot.getChildren()){
                    String currentTitle = currentSnapshot.child("title").getValue(String.class);
                    String currentDescription = currentSnapshot.child("description").getValue(String.class);
                    tasks.add(new information(currentTitle, currentDescription, currentSnapshot.getKey(), id));
                    tasks_recyclerViewAdapter adapter = new tasks_recyclerViewAdapter(MainActivity.this, tasks);
                    MainActivity.this.recyclerView.setAdapter(adapter);
                    MainActivity.this.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                }
                TextView numberOftasks = findViewById(R.id.numberOfTasks);
                if (tasks.size() > 1){
                    numberOftasks.setText(tasks.size() + " tasks");
                }
                else{
                    numberOftasks.setText(tasks.size() + " task");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:

                firebaseAuth.signOut();
                Intent intent = new Intent(this, signInActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    public void addTaskActivity(String id){
        Intent intent = new Intent(this, addTask.class);
        intent.putExtra("id",id);
        startActivityForResult(intent, TEXT_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST){
                String title = data.getStringExtra(addTask.EXTRA_TITLE);
                String description = data.getStringExtra(addTask.EXTRA_Description);
                String key = data.getStringExtra(addTask.EXTRA_KEY);
                String userId = data.getStringExtra(addTask.EXTRA_USERID);
                if (title != null && description != null){
                    tasks.add(new information(title, description,key, userId));
                    tasks_recyclerViewAdapter adapter = new tasks_recyclerViewAdapter(this, tasks);
                    this.recyclerView.setAdapter(adapter);
                    this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                }
                TextView numberOftasks = findViewById(R.id.numberOfTasks);
                if (tasks.size() > 1){
                    numberOftasks.setText(tasks.size() + " tasks");
                }
                else{
                    numberOftasks.setText(tasks.size() + " task");
                }


        }
        if(resultCode == 2){
            String title = data.getStringExtra(addTask.EXTRA_TITLE);
            String description = data.getStringExtra(addTask.EXTRA_Description);
            Integer pos = data.getIntExtra("pos",0);
            Applicationclass applicationclass = (Applicationclass) this.getApplication();
            tasks_recyclerViewAdapter adapter = applicationclass.getAdapter();

            //update to the database
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            information editTheTask = new information(title.toString(), description.toString());
            firebaseDatabase.getReference().child("tasks").child(adapter.tasks.get(pos).getUserId()).child(adapter.tasks.get(pos).getTaskId()).setValue(null);
            firebaseDatabase.getReference().child("tasks").child(adapter.tasks.get(pos).getUserId()).child(adapter.tasks.get(pos).getTaskId()).setValue(editTheTask);

            adapter.tasks.get(pos).setTitle(title);
            adapter.tasks.get(pos).setDescription(description);
            adapter.notifyItemChanged(pos);
            Toast.makeText(getApplicationContext(),"Updated the task",Toast.LENGTH_SHORT).show();

        }
    }


}