package com.example.majdoorhub;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public static String PREFS_NAME = "MyPrefsFile";
    Button createNew;
    EditText inputEmail, inputPassword, phone;
    Button loginbtn;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    TextView forgotPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNew = findViewById(R.id.createnew);
        inputEmail = findViewById(R.id.usertxtemail);
        inputPassword = findViewById(R.id.usertextpassword);
        loginbtn = findViewById(R.id.buttonlg);
        forgotPassword = findViewById(R.id.textView);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        loginbtn.setOnClickListener(e ->
        {
            performLogin();
        });



        createNew.setOnClickListener(e -> {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
        });

    }
    private void performLogin() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    sendUsertonextActivity();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void sendUsertonextActivity() {

        Intent i = new Intent(MainActivity.this,Services.class);
        i.setFlags(i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);


    }
}