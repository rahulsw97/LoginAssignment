package com.rahulswami_.loginassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Registration extends AppCompatActivity {

    EditText regName, regNumber, regEmail, regPass, regCpass;
    TextView regLogin;
    Button reg;

    //ProgressBar progressBar;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regName = findViewById(R.id.name);
        regNumber = findViewById(R.id.number);
        regEmail = findViewById(R.id.email);
        regPass = findViewById(R.id.password);
        regCpass = findViewById(R.id.cpassword);
        regLogin = findViewById(R.id.login);
        reg = findViewById(R.id.register);
       /// progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //progressBar.setVisibility(View.GONE);


        regLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Registration.this,Login.class);
                startActivity(i);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUser();

            }
        });

    }

    private void registerUser() {

        String Name = regName.getText().toString().trim();
        String Number = regNumber.getText().toString().trim();
        String Email = regEmail.getText().toString().trim();
        String Password = regPass.getText().toString().trim();
        String Confirm_Password = regCpass.getText().toString().trim();
        String mailPattern = "[a-zA-Z 0-9_-]+@[a-z]+\\.+[a-z]+";


        if (Name.equals("")||Name.length()<3)
        {
            regName.setError("Enter Valid Name");

        }
        else if (Number.equals("") || Number.length() != 10)
        {
            regNumber.setError("Provide Valid Number");

        }
        else if (Email.equals("")||!Email.matches(mailPattern))
        {
            regEmail.setError("Provide Valid Email");
        }
        else if (Password.equals("")||Password.length()<6)
        {
            regPass.setError("Password Length Should Be 6 Char");
        }
        else if (!Confirm_Password.equals(Password))
        {
            regCpass.setError("Password Not Matching");
        }
        else {

           // progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                User user = new User(Name,Number,Email);
                                FirebaseDatabase.getInstance().getReference("loginassignment-51390-default-rtdb")
                                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful())
                                        {
                                            //progressBar.setVisibility(View.GONE);
                                            Toast.makeText(Registration.this, "Successful", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(Registration.this,Login.class);
                                            startActivity(i);

                                        }
                                        else {
                                            Toast.makeText(Registration.this, "Failed", Toast.LENGTH_SHORT).show();
                                           // progressBar.setVisibility(View.GONE);
                                        }

                                    }
                                });

                            }
                            else
                            {
                                Toast.makeText(Registration.this, "Something Went's Wrong", Toast.LENGTH_SHORT).show();
                               // progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
        }


    }
}