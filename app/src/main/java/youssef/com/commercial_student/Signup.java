package youssef.com.commercial_student;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    ProgressBar progressBar,proimagee;



    EditText emailedit,passwordedit,Retypingpass,Fname,Lname;
    Button signupp;

     String downloadedurl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        emailedit=(EditText)findViewById(R.id.useremail);
        passwordedit=(EditText)findViewById(R.id.password);
        Retypingpass=(EditText)findViewById(R.id.retypingpassword);
        signupp=(Button)findViewById(R.id.regist);
        mAuth=FirebaseAuth.getInstance();
        progressBar=(ProgressBar)findViewById(R.id.probar);






        signupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();


            }
        });





    }



    private  void registeruser()
    {
        String email=emailedit.getText().toString();
        String passwordd=passwordedit.getText().toString().trim();
        String retpingpass=Retypingpass.getText().toString().trim();


        if (email.isEmpty())
        {
            emailedit.setError("email is required");
            emailedit.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailedit.setError("please Enter a valid email");
            emailedit.requestFocus();
            return;
        }
        if (passwordd.isEmpty())
        {
            passwordedit.setError("Password is required");
            passwordedit.requestFocus();
            return;
        }
        if (passwordd.length()<6)
        {
            passwordedit.setError("Minimum Length of password should be 6");
            passwordedit.requestFocus();
            return;
        }
        if (retpingpass.isEmpty())
        {
            Retypingpass.setError("Password is required");
            Retypingpass.requestFocus();
            return;
        }
        if (retpingpass.length()<6) {
            Retypingpass.setError("Minimum Length of password should be 6");
            Retypingpass.requestFocus();
            return;
        }

        if (!retpingpass.equals(passwordd))
        {
            Retypingpass.setError("Not match password");
            Retypingpass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        signupp.setClickable(false);
        signupp.setEnabled(false);
        signupp.setBackgroundColor(Color.WHITE);
        mAuth.createUserWithEmailAndPassword(email,passwordd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                        progressBar.setVisibility(View.GONE);
                        signupp.setClickable(true);
                        signupp.setEnabled(true);
                        signupp.setBackgroundColor(Color.BLACK);
                        if (task.isSuccessful()) {
                            Toast.makeText(Signup.this,"Successful registration",Toast.LENGTH_LONG).show();

                            finish();
                            Intent o=new Intent(Signup.this,Profile.class);
                            o.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(o);
                        } else {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });

    }


}
