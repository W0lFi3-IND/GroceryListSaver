package com.wolfie.grocerylistsaver;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText email;
    EditText pass;
    private FirebaseAuth mAuth;
     ProgressBar simpleProgressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.emailR);
        pass = findViewById(R.id.passwordR);
        mAuth=FirebaseAuth.getInstance();
        ImageButton btn = findViewById(R.id.Login);
        ImageButton btn1= findViewById(R.id.button);
         simpleProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleProgressBar.setVisibility(View.VISIBLE);
                simpleProgressBar.setProgress(1000);
                loginuser();
                timer();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Registration.class));
            }
        });


    }

    void timer(){
        new CountDownTimer(1000,100){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                simpleProgressBar.setVisibility(View.INVISIBLE);
            }
        }.start();
    }



    private void loginuser() {
        String Email=email.getText().toString();
        String Pass = pass.getText().toString();
        if (TextUtils.isEmpty(Email)|| TextUtils.isEmpty(Pass)) {
            Toast.makeText(getApplicationContext(), "Email Address or Password cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Email.contains("@"))
        {
            Toast.makeText(getApplicationContext(), "Enter valid email address", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(Email, Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(),Mainscreen.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FAILED", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "WRONG Email OR Password",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        // ...
                    }
                });

    }
}
