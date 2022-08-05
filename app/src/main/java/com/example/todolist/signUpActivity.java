package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signUpActivity extends AppCompatActivity {
    private Button signUpBtn;
    private EditText email;
    private  EditText password;
    private EditText username;
    private TextView accountCreated;


    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //setup firebase
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(signUpActivity.this);
        progressDialog.setTitle("Creating the Account");
        progressDialog.setMessage("Account is being created");

        //getting all buttons and editText
        signUpBtn = findViewById(R.id.signInBtn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password_edit);
        username = findViewById(R.id.Email);
        accountCreated = findViewById(R.id.accountCreated);

        accountCreated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signUpActivity.this, signInActivity.class);
                startActivity(intent);
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()){
                                    Users user = new Users(username.getText().toString(), email.getText().toString(), password.getText().toString());
                                    String id = task.getResult().getUser().getUid();
                                    database.getReference("Users").child(id).setValue(user);
                                    Intent intent = new Intent(signUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(signUpActivity.this, "User was created successfully", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(signUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}