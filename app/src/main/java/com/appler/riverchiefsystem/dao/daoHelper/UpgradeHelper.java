package com.appler.riverchiefsystem.dao.daoHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.riverchiefsystem.greendao.gen.AdminDataDao;
import com.riverchiefsystem.greendao.gen.DaoMaster;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;


public class UpgradeHelper extends DaoMaster.OpenHelper {

    public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Database database = new StandardDatabase(db);
        MigrationHelper.getInstance().migrate(database, AdminDataDao.class);
    }
}
