package com.lmtech.infrastructure.dao;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.model.ApkPackage;

/**
 * 移动端安装包Dao
 * Created by huang.jb on 2017-3-28.
 */
public interface ApkPackageDao extends Dao<ApkPackage> {
    /**
     * 查询最新的移动端安装包
     * @param type
     * @return
     */
    ApkPackage selectLatestApkPackage(String type);
}
