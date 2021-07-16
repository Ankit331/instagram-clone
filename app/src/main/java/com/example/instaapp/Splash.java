package com.example.instaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                // This method will be executed once the timer is over
//                Intent i = new Intent(com.example.instaapp.Splash.this, LoginActivity.class);
//                startActivity(i);
//                finish();

                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    startActivity(new Intent(com.example.instaapp.Splash.this, MainActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(com.example.instaapp.Splash.this, LoginActivity.class));
                    finish();
                }
            }
        }, 5000);
    }
}