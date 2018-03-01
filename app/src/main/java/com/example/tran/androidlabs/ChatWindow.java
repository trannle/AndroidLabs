package com.example.tran.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    ListView view;
    Button send;
    EditText data;
    ArrayList <String>arrayData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        this.view = findViewById(R.id.view);
        this.send = findViewById(R.id.buttonSend);
        this.data = findViewById(R.id.insertData);

        final ChatAdapter messageAdapter = new ChatAdapter(this);
        view.setAdapter(messageAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = data.getText().toString();
                arrayData.add(item);

                messageAdapter.notifyDataSetChanged();
                data.setText("");

            }
        });

    }

    private class ChatAdapter extends ArrayAdapter<String>{

        public ChatAdapter(Context ctx){
            super(ctx,0);
        }

        public int getCount(){
            return arrayData.size();
        }

        public String getItem(int position){

            return arrayData.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null ;

            if(position%2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            }

            else{
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;
        }

        public long getInt(int position){
            return position;
        }


     }

    }

