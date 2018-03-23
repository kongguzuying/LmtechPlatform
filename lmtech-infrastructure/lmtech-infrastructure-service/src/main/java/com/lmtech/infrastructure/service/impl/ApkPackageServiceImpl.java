package com.lmtech.infrastructure.service.impl;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.dao.ApkPackageDao;
import com.lmtech.infrastructure.model.ApkPackage;
import com.lmtech.infrastructure.service.ApkPackageService;
import com.lmtech.service.support.AbstractDbServiceBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huang.jb on 2017-3-28.
 */
@Service
public class ApkPackageServiceImpl extends AbstractDbServiceBaseImpl<ApkPackage> implements ApkPackageService {

    @Autowired
    private ApkPackageDao apkPackageDao;

    @Override
    public Dao<ApkPackage> getDao() {
        return apkPackageDao;
    }

    @Override
    public ApkPackage queryLatestApkPackage(String type) {
        return apkPackageDao.selectLatestApkPackage(type);
    }
}
