package com.lmtech.infrastructure.service;

import com.lmtech.infrastructure.model.ApkPackage;
import com.lmtech.service.DbServiceBase;

/**
 * 移动端安装包数据服务
 * Created by huang.jb on 2017-3-28.
 */
public interface ApkPackageService extends DbServiceBase<ApkPackage> {
    /**
     * 查询最新移动端安装包
     * @param type
     * @return
     */
    ApkPackage queryLatestApkPackage(String type);
}
