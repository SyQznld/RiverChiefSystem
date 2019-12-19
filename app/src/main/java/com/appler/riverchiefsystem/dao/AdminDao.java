package com.appler.riverchiefsystem.dao;

import com.appler.riverchiefsystem.base.BaseApplication;
import com.appler.riverchiefsystem.dao.daoBean.AdminData;

import java.util.List;

/**
 * 登录用户 dao
 */

public class AdminDao {

    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param data
     */
    public static void insertAdminData(AdminData data) {
        BaseApplication.getDaoSession().getAdminDataDao().insert(data);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteAdminData(long id) {
        BaseApplication.getDaoSession().getAdminDataDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param data
     */
    public static void updateAdminData(AdminData data) {
        BaseApplication.getDaoSession().getAdminDataDao().update(data);
    }


    /**
     * 查询全部数据
     */
    public static List<AdminData> queryAll() {
        return BaseApplication.getDaoSession().getAdminDataDao().loadAll();
    }


    public static int getAdminID() {
        List<AdminData> accountTables = BaseApplication.getDaoSession().getAdminDataDao().loadAll();
        if (accountTables.size() > 0) {
            AdminData adminData = accountTables.get(0);
            return adminData.getUserId();
        }
        return 0;

    }

    public static AdminData getAdmin() {
        List<AdminData> accountTables = BaseApplication.getDaoSession().getAdminDataDao().loadAll();
        if (accountTables.size() > 0) {
            AdminData adminData = accountTables.get(0);
            return adminData;
        }
        return null;

    }


    /**
     * 删除全部数据
     */
    public static void deleAllData() {
        BaseApplication.getDaoSession().getAdminDataDao().deleteAll();
    }

}
