package com.lmtech.infrastructure.service.impl;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.dao.ApkPackageDao;
import com.lmtech.infrastructure.model.ApkPackage;
import com.lmtech.infrastructure.service.ApkPackageManager;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huang.jb on 2017-3-28.
 */
@Service
public class ApkPackageManagerImpl extends AbstractDbManagerBaseImpl<ApkPackage> implements ApkPackageManager {

    @Autowired
    private ApkPackageDao apkPackageDao;

    @Override
    public Dao<ApkPackage> getDao() {
        return apkPackageDao;
    }
}
