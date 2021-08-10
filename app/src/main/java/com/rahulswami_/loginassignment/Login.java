package com.rahulswami_.loginassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText  regEmail, regPass;
    TextView logReg;
    Button log;
    ImageView fb;

    ProgressBar progressBar;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        regEmail = findViewById(R.id.email);
        regPass = findViewById(R.id.password);
        logReg = findViewById(R.id.register);
        log = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);
        fb = findViewById(R.id.imageView2);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        progressBar.setVisibility(View.GONE);

        logReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Login.this,Registration.class);
                startActivity(i);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginToAccount();
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Login.this,FaceBook.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        });

    }

    private void loginToAccount() {

        String Email = regEmail.getText().toString().trim();
        String Password = regPass.getText().toString().trim();
        String mailPattern = "[a-zA-Z 0-9_-]+@[a-z]+\\.+[a-z]+";

         if (Email.equals("")||!Email.matches(mailPattern))
        {
            regEmail.setError("Provide Valid Email");
        }
        else if (Password.equals("")||Password.length()<6)
        {
            regPass.setError("Password Length Should Be 6 Char");
        }
        else{

            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Login.this,MainActivity.class);
                                startActivity(i);
                            }
                            else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

         }

    }
}