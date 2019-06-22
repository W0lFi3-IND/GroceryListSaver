package com.wolfie.grocerylistsaver;

import android.content.Intent;
import android.os.Bundle;
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

public class Registration extends AppCompatActivity {
    EditText email;
    EditText pass,cpass;
    private FirebaseAuth mAuth;
    ProgressBar simpleProgressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email = findViewById(R.id.err);
        pass = findViewById(R.id.prr);
        cpass=findViewById(R.id.cnfrr);
        mAuth=FirebaseAuth.getInstance();
        ImageButton btn = findViewById(R.id.Regbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg();

            }
        });

    }

    void reg()
    {   String Email=email.getText().toString();
        String Pass = pass.getText().toString();
        String PassCP=cpass.getText().toString();
        if (TextUtils.isEmpty(Email)|| TextUtils.isEmpty(Pass)) {
            Toast.makeText(getApplicationContext(), "Email Address or Password cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Email.contains("@"))
        {
            Toast.makeText(getApplicationContext(), "Enter valid email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Pass.length()<8)
        {
            Toast.makeText(getApplicationContext(), "Password length is less than 8", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Pass.equals(PassCP)){

            Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();
            return;

        }
        mAuth.createUserWithEmailAndPassword(Email,Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(),Mainscreen.class));


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("failed", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Problem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
