package com.lmtech.infrastructure.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.infrastructure.dao.ApkPackageDao;
import com.lmtech.infrastructure.mapper.ApkPackageMapper;
import com.lmtech.infrastructure.model.ApkPackage;
import org.springframework.stereotype.Service;

/**
 * Created by huang.jb on 2017-3-28.
 */
@Service
public class ApkPackageDaoImpl extends MyBatisDaoBase<ApkPackageMapper, ApkPackage> implements ApkPackageDao {
    @Override
    public ApkPackage selectLatestApkPackage(String type) {
        return baseMapper.selectLatestApkPackage(type);
    }
}
