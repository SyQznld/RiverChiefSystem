package com.appler.riverchiefsystem.loginHis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginHisHelper extends SQLiteOpenHelper {

    public LoginHisHelper(Context context) {
        super(context, "dlqwgl.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE account(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userName VARCHAR(20)," +
                "pass VARCHAR(20)," +
                "fullName VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}