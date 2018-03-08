package com.example.tran.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Tran on 2018-03-02.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Messages.db";
    public static final int VERSION_NUM = 6;
    public static final String KEY_ID="ID";
    public static final String KEY_MESSAGE="Messages";
    public static final String TABLE_NAME="ChatData";

    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        //String CREATE_CHAT_TABLE="CREATE TABLE "+ TABLE_NAME +KEY_ID +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_MESSAGE+" TEXT";
        db.execSQL("CREATE TABLE "+TABLE_NAME+"("+KEY_ID+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_MESSAGE+" TEXT)");
        //db.execSQL(CREATE_CHAT_TABLE);
    }

    public void onUpgrade (SQLiteDatabase db, int oldVer, int newVer){
        Log.i("ChatDatabaseHelper", "Calling OnUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer){
        Log.i("ChatDatabaseHelper", "Calling onDowngrade");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }
    public void onOpen(SQLiteDatabase db){
        Log.i("ChatDatabaseHelper","DATABASE opened");
    }

}

