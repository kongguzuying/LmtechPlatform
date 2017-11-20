package com.lmtech.admin.common.controller;

import com.lmtech.admin.common.adaptor.ApkPackageAdaptor;
import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.infrastructure.model.ApkPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * Apk管理控制器
 * Created by huang.jb on 2016-8-17.
 */
@Controller
@RequestMapping(value = "/platform/apk")
public class ApkController extends ManagerControllerBase<ApkPackage> {

    @Autowired
    private ApkPackageAdaptor apkPackageAdaptor;

    @Override
    public ControllerManager<ApkPackage> getManager() {
        return apkPackageAdaptor;
    }

    @Override
    public void fillEntity(ApkPackage dbEntity, ApkPackage pageEntity) {
        dbEntity.setApkCode(pageEntity.getApkCode());
        dbEntity.setApkDesc(pageEntity.getApkDesc());
        dbEntity.setApkDownLoad(pageEntity.getApkDownLoad());
        dbEntity.setApkFileName(pageEntity.getApkFileName());
        dbEntity.setApkImageDownLoad(pageEntity.getApkImageDownLoad());
        dbEntity.setApkImageLoacalPath(pageEntity.getApkImageLoacalPath());
        dbEntity.setApkImageName(pageEntity.getApkImageName());
        dbEntity.setApkLocalPath(pageEntity.getApkLocalPath());
        dbEntity.setApkName(pageEntity.getApkName());
        dbEntity.setApkSize(pageEntity.getApkSize());
        dbEntity.setApkType(pageEntity.getApkType());
        dbEntity.setApkVersion(pageEntity.getApkVersion());
        dbEntity.setDeleteDb(pageEntity.isDeleteDb());
        dbEntity.setDelFlag(pageEntity.isDelFlag());
        dbEntity.setHaveBestNew(pageEntity.isHaveBestNew());
        dbEntity.setUpdateType(pageEntity.getUpdateType());
    }

    @Override
    public String getRequestMapping() {
        return "platform/apk";
    }

    @RequestMapping(value = "/download.do")
    public ModelAndView download() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(getRequestMapping() + "/download");

        return mav;
    }

    @RequestMapping(value = "/downloadapk.do")
    public ModelAndView downloadApk(HttpServletResponse response, @RequestParam String type) {
        /*try {
            ApkPackage apkInfo = apkPackageAdaptor.queryLastApkPackage(type);
            if (apkInfo != null) {
                OSSUtil.Oss oss = new OSSUtil.Oss(apkInfo.getApkDownLoad());
                //OSSObject ossObject = OSSUtil.download(oss);
                byte[] bytes = null;// StreamUtil.readStreamBytes(ossObject.getObjectContent());
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(apkInfo.getApkFileName(), "UTF-8"));
                ServletOutputStream os = response.getOutputStream();
                os.write(bytes);
                os.flush();
            } else {
                response.getWriter().write("没有发现可用的Apk版本。");
            }
        } catch (Exception e) {
            LoggerManager.error(e);
        }*/
        return null;
    }
}
