package com.appler.riverchiefsystem.loginHis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    public final static String TABLE_NAME = "account";
    private LoginHisHelper helper;
    private String userName;

    public AccountDao(Context context) {
        helper = new LoginHisHelper(context);
    }

    public void insert(LoginHistoryData info) {
        SQLiteDatabase db = helper.getWritableDatabase();
        //根据手机号判断去重
        String[] colum = {"userName"};
        String where = "userName" + "= ?";
        String[] whereValue = {info.getUserName()};
        Cursor cursor = db.query(TABLE_NAME, colum, where, whereValue, null, null, null);
        while (cursor.moveToNext()) {
            userName = cursor.getString(cursor.getColumnIndex("userName"));
        }
        cursor.close();
        ContentValues values = new ContentValues();
        values.put("userName", info.getUserName());
        values.put("pass", info.getPass());
        if (!TextUtils.isEmpty(userName)) {

            db.update(TABLE_NAME, values, "userName" + "=?", new String[]{userName});
        } else {
            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }

    public int delete(String userName) {
//        table：代表想删除数据的表名。
//        whereClause：满足该whereClause子句的记录将会被删除。
//        whereArgs：用于为whereArgs子句传入参数。
//        删除person_inf表中所有人名以孙开头的记录
//        int result=db.delete("person_inf","person_name like ?",new String[]{"孙_"});
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete(TABLE_NAME, "userName=?", new String[]{userName + ""});
        db.close();
        return count;
    }


    public void deleteNoCurrAccount(String userName) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_NAME, "userName like ?", new String[]{userName});
        db.close();
    }

    public List<LoginHistoryData> queryAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        List<LoginHistoryData> list = new ArrayList();

        while (cursor.moveToNext()) {
            LoginHistoryData historyInfo = new LoginHistoryData();
            historyInfo.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
            historyInfo.setPass(cursor.getString(cursor.getColumnIndex("pass")));
            list.add(historyInfo);
        }

        db.close();
        cursor.close();
        return list;
    }
}
