package com.example.tran.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;

public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME ="LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences prefs = getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        String userEmail = prefs.getString("DefaultEmail", "email@domain.com");
        final SharedPreferences.Editor edit = prefs.edit();


        final EditText emailLogin;
        emailLogin = findViewById(R.id.editText3);
        emailLogin.setText(userEmail);

        Button loginButton = findViewById(R.id.button2);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = emailLogin.getText().toString();
                edit.putString("DefaultEmail",login);
                edit.commit();


                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onResume(){
        Log.i(ACTIVITY_NAME, "Im onResume()");
        super.onResume();
    }

    @Override
    protected void onStart(){
        Log.i(ACTIVITY_NAME, "Im onStart()");
        super.onStart();
    }
    @Override
    protected void onPause(){
        Log.i(ACTIVITY_NAME, "Im onPause()");
        super.onPause();
    }

    @Override
    protected void onStop(){
        Log.i(ACTIVITY_NAME, "Im onStop()");
        super.onStop();
    }
    protected void OnDestroy(){
        Log.i(ACTIVITY_NAME, "Im onDestroy()");
        super.onDestroy();

    }
}

