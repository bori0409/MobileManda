package com.example.myapplicationaaaa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*public class LogIn  extends AppCompatActivity{
    private static final String logtag = "NOWLOG";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
       // updateUI(currentUser);
    }
    public void GoBack(){
        finish();
    }
    public void signIn(){

        EditText email = findViewById(R.id.editText7);
        String emailStr  = email.getText().toString();
        Log.d(logtag, emailStr);
        EditText password = findViewById(R.id.editText8);
        String passStr  = password.getText().toString();
        Log.d(logtag, passStr);

        mAuth.signInWithEmailAndPassword(emailStr, passStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(logtag, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                                Toast.makeText(getBaseContext(), "Successfully logged in ", Toast.LENGTH_SHORT).show();
                                FirebaseUser usernow = FirebaseAuth.getInstance().getCurrentUser();

                                // Name, email address, and profile photo Url

                                String email1 = user.getEmail();
                                Intent info = new Intent(getBaseContext(), BookingActivity.class);
                                info.putExtra("email", email1);

                                String uid = user.getUid();
                                info.putExtra("id", uid);
                                Log.d(logtag, uid);
                                startActivity(info);

                                String UID = mAuth.getUid();
                              Intent gotobooking =new Intent(getBaseContext(),MainActivity.class);
                              gotobooking.putExtra("UID",UID );
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(logtag, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getBaseContext(), "Unsuccessful  ", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                //  Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }


                        }});

    }
  /* public void signIn(View view){

       EditText email = findViewById(R.id.editText7);
       String emailStr  = email.getText().toString();
       EditText password = findViewById(R.id.editText8);
       String passStr  = email.getText().toString();
       FirebaseAuth.getInstance()
   }*/



public class LogIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText emailfield, passwordfield;
    //Button login;
    private static final String TAG = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();
        emailfield = findViewById(R.id.editText7);
        passwordfield = findViewById(R.id.editText8);

    }

    public void buttonclick(View view) {
        String email = emailfield.getText().toString();
        String password = passwordfield.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LogIn.this, BookingActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                        // ...
                    }
                });




    }}

