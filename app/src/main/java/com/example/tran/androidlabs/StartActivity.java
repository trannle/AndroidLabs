package com.example.tran.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME ="StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button button = findViewById(R.id.button);

        Button weatherButton = findViewById(R.id.weatherButton);

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, WeatherForecast.class);
                startActivity(intent);
            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, activity_list_items.class);
                startActivityForResult(intent, 50);

         }

        });

        Button buttonChat = findViewById(R.id.startChat);

        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");

                Intent intent1 = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(intent1);
            }
        });

    }


    @Override
    protected void onResume(){
        Log.i(ACTIVITY_NAME, "In onResume()");
        super.onResume();
    }

    @Override
    protected void onStart(){
        Log.i(ACTIVITY_NAME, "In onStart()");
        super.onStart();
    }
    @Override
    protected void onPause(){
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }

    @Override
    protected void onStop(){
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }
    protected void OnDestroy(){
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();

    }
    public void onActivityResult( int requestCode, int resultCode, Intent data){
       if(requestCode==50){
           Log.i(ACTIVITY_NAME,"Returned to StartActivity.OnActivityResult");
       }
       if(resultCode==Activity.RESULT_OK){
           String messagePassed = data.getStringExtra("Response");
           Toast toast = Toast.makeText(this , messagePassed, Toast.LENGTH_SHORT);
           toast.show();
       }



    }
}
