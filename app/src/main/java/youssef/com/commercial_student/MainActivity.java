package youssef.com.commercial_student;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText user_email,user_password;
    Button log_in;
    TextView app_title;
    Typeface titlestyle;
   private FirebaseAuth mAuth;
    ArrayList<String> ss=new ArrayList<>();
    LinearLayout gosignup;
    ProgressBar loginpro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //intialization views

        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        user_email=(EditText)findViewById(R.id.getemail);
        user_password=(EditText)findViewById(R.id.getpass);
        app_title=(TextView)findViewById(R.id.apptitle);
        log_in=(Button)findViewById(R.id.Loginbu);
        titlestyle=Typeface.createFromAsset(getAssets(),"fonts/arfont.ttf");
        app_title.setTypeface(titlestyle);
        gosignup=(LinearLayout)findViewById(R.id.signuplayout);
        loginpro=(ProgressBar)findViewById(R.id.progresslogin);






        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginuser();
            }
        });

        gosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent z=new Intent(MainActivity.this,Signup.class);
                startActivity(z);
            }
        });

    }

    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(MainActivity.this, Home.class));
        }
    }
    private void loginuser()
    {
        String email=user_email.getText().toString();
        String passwordd=user_password.getText().toString();
        if (email.isEmpty())
        {
            user_email.setError("Email is required");
            user_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            user_email.setError("please Enter a valid email");
            user_email.requestFocus();
            return;
        }
        if (passwordd.isEmpty())
        {
            user_password.setError("Password is required");
            user_password.requestFocus();
            return;
        }
        if (passwordd.length()<6)
        {
            user_password.setError("Minimum Length of password should be 6");
            user_password.requestFocus();
            return;
        }
        loginpro.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,passwordd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loginpro.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            finish();
                            Intent i=new Intent(MainActivity.this,Home.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

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
