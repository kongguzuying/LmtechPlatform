package com.lmtech.infrastructure.mapper;

import com.lmtech.dao.LmtechBaseMapper;
import com.lmtech.infrastructure.model.ApkPackage;
import org.apache.ibatis.annotations.Param;

/**
 * 移动端安装包Mapper
 * Created by huang.jb on 2017-3-28.
 */
public interface ApkPackageMapper extends LmtechBaseMapper<ApkPackage> {
    /**
     * 查询最新的移动端安装包
     * @param type
     * @return
     */
    ApkPackage selectLatestApkPackage(@Param("type") String type);
}
