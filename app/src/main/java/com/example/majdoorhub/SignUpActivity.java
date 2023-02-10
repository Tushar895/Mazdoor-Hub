package com.example.majdoorhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    TextView signIn;
    TextView createAccount;
    EditText userName,userPhoneNo;
    EditText inputEmail, inputPasswaord, inputconfirmPassword;
    Button createAcc;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    // String PASSWORD_PATTERN = "^(?=.[0-9])(?=.[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore fStore;
    String phone,email,username,password;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signIn = findViewById(R.id.textvsignin);
        userName = findViewById(R.id.usertextname);
        userPhoneNo = findViewById(R.id.userphonenumber);
        fStore = FirebaseFirestore.getInstance();

        inputEmail = findViewById(R.id.usertxtemail);
        inputPasswaord =findViewById(R.id.usertextpassword);
        inputconfirmPassword = findViewById(R.id.userconfirmpassword);
        createAcc = findViewById(R.id.btncreateaccount);
        signIn.setOnClickListener(e->{
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        });
        createAccount = findViewById(R.id.txtview);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        createAcc.setOnClickListener(e->{
            PerformAuth();
        });


    }


    private void PerformAuth() {
        String email = inputEmail.getText().toString();
        String password = inputPasswaord.getText().toString();
        String confirmPassword = inputconfirmPassword.getText().toString();
        if(!email.matches(emailPattern))
        {
            inputEmail.setError("Enter Correct Email");
        }
        /*
        else if(!password.matches(PASSWORD_PATTERN))
        {
            inputPasswaord.setError("Enter Strong Pattern");
        }

         */


        else if(password.isEmpty() || password.length()<6)
        {
            inputPasswaord.setError("Enter Strong Pattern");
        }
        else if(!password.equals(confirmPassword))
        {
            inputconfirmPassword.setError("Password Not Match");
        }
        else
        {
            progressDialog.setMessage("Please wait while Creating Account");
            progressDialog.setTitle("Create Account");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isComplete())
                    {
                        userdataInsertion();
                        progressDialog.dismiss();
                        sendUsertonextActivity();
                        Toast.makeText(SignUpActivity.this,"Registration Succesfull",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, "" + task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void userdataInsertion() {
        email= inputEmail.getText().toString();
        phone= "+91" + userPhoneNo.getText().toString();
        username= userName.getText().toString();
        HashMap<String,Object> signup_Map = new HashMap<>();
        signup_Map.put("Email",email);
        signup_Map.put("Name",username);
        signup_Map.put("PhoneNumber",phone);

        DocumentReference df = fStore.collection("Users").document(mUser.getUid());
        df.set(signup_Map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SignUpActivity.this, "User data firebase data saved", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendUsertonextActivity() {

        Intent i = new Intent(SignUpActivity.this,Services.class);
        i.setFlags(i.FLAG_ACTIVITY_CLEAR_TASK|i.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);



    }
}