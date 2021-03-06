package com.example.tran.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
    ArrayList<String> arrayData = new ArrayList<>();
    ChatDatabaseHelper chatHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Chat Window", "In onCreate()");
        setContentView(R.layout.activity_chat_window);

        this.chatHelper = new ChatDatabaseHelper(this);
        SQLiteDatabase db = chatHelper.getWritableDatabase();

        this.view = findViewById(R.id.view);
        this.send = findViewById(R.id.buttonSend);
        this.data = findViewById(R.id.insertData);

        final ChatAdapter messageAdapter = new ChatAdapter(this);
        view.setAdapter(messageAdapter);
        Cursor c = db.query(ChatDatabaseHelper.TABLE_NAME, new String[]{ChatDatabaseHelper.KEY_MESSAGE},null,null,null,null,null);
        int colIndex = c.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);

        Log.i("ChatWindow", "Cursor’s  column count =" + c.getColumnCount());
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String message = c.getString(colIndex);
            arrayData.add(message);
            Log.i("ChatWindow", "message retrieved from Cursor" + message);
            c.moveToNext();
        }

        data.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCodePressed, KeyEvent event) {

                if (keyCodePressed == event.KEYCODE_ENTER) {

                    send.performClick();
                }
                return false;
                }
            });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Chat Window", "ChatHelper opened db");
                SQLiteDatabase db = chatHelper.getWritableDatabase();
                String item = data.getText().toString();
                arrayData.add(item);
                ContentValues newData = new ContentValues();
                newData.put(ChatDatabaseHelper.KEY_MESSAGE, item);

                db.insert(ChatDatabaseHelper.TABLE_NAME, null, newData);
                messageAdapter.notifyDataSetChanged();
                data.setText("");

            }


        });
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return arrayData.size();
        }

        public String getItem(int position) {

            return arrayData.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;

            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;
        }

        public long getInt(int position) {
            return position;
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

