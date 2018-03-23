package com.lmtech.infrastructure.facade.stub;

import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.ApkPackageQueryRequest;
import com.lmtech.infrastructure.facade.request.ApkPackageRequest;
import com.lmtech.infrastructure.facade.response.ApkPackageListResponse;
import com.lmtech.infrastructure.facade.response.ApkPackageResponse;

/**
 * 移动端安装包数据服务入口
 * Created by huang.jb on 2017-3-27.
 */
/*@FeignClient(name = "lmtech-infrastructure")
@RequestMapping(value = "/apkpackage")*/
public interface ApkPackageFacade {
    /**
     * 查询最新版本的移动端安装包
     * @param request
     * @return
     */
    ApkPackageResponse queryLatestApkPackage(StringRequest request);

    /**
     * 获取移动端安装包数据
     * @param request
     * @return
     */
    ApkPackageResponse getApkPackage(StringRequest request);

    /**
     * 获取所有移动端安装包数据
     * @param request
     * @return
     */
    ApkPackageListResponse getAllApkPackage(ApkPackageQueryRequest request);

    /**
     * 添加移动端安装包数据
     * @param request
     * @return
     */
    StringResponse addApkPackage(ApkPackageRequest request);

    /**
     * 编辑移动端安装包数据
     * @param request
     * @return
     */
    StringResponse editApkPackage(ApkPackageRequest request);

    /**
     * 删除移动端安装包数据
     * @param request
     * @return
     */
    NormalResponse removeApkPackage(StringRequest request);
}
