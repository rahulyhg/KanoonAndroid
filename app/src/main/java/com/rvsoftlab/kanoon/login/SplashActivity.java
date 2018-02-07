package com.rvsoftlab.kanoon.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rvsoftlab.kanoon.R;
import com.rvsoftlab.kanoon.helper.SessionManager;
import com.rvsoftlab.kanoon.home.MainActivity;

import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        session = new SessionManager(this);
        if (mAuth.getCurrentUser()==null){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }else {
            if (session.isUsernameSet()){
                startActivity(new Intent(this,MainActivity.class));
                finish();
            }else {
                startActivity(new Intent(this,LoginActivity.class));
                finish();
            }
        }
    }


}
