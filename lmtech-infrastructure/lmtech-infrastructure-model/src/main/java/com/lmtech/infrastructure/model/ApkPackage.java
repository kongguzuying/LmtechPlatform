package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * Apk包
 * Created by huang.jb on 2016-8-17.
 */
@TableName("LM_APK_PACKAGE")
public class ApkPackage extends DbEntityBase {

    public static final String APKTYPE_ANDROID = "Android";
    public static final String APKTYPE_IOS = "IOS";

    @TableField("APKCODE")
    private String apkCode;//APK编号
    @TableField("APKNAME")
    private String apkName;//APK名字
    @TableField("APKDESC")
    private String apkDesc;//APK描述
    @TableField("APKFILENAME")
    private String apkFileName;//APK文件名
    @TableField("APKTYPE")
    private String apkType;//APK类型
    @TableField("APKVERSION")
    private String apkVersion;//APK版本
    @TableField("APKLOCALPATH")
    private String apkLocalPath;//APK存放在本地路径
    @TableField("APKDOWNLOAD")
    private String apkDownLoad;//APK下载地址
    @TableField("APKIMAGENAME")
    private String apkImageName;//APK图片名字
    @TableField("APKIMAGELOACALPATH")
    private String apkImageLoacalPath;//APK图片本地路径
    @TableField("APKIMAGEDOWNLOAD")
    private String apkImageDownLoad;//APK对应图片下载地址
    @TableField("HAVEBESTNEW")
    private boolean haveBestNew;//是否最新版本
    @TableField("DELFLAG")
    private boolean delFlag;//APK删除标志
    @TableField("DELETEDB")
    private boolean deleteDb;//是否删除db
    @TableField("UPDATETYPE")
    private String updateType;//是否强制更新   S-必须更新    G-建议更新    N-无更新
    @TableField("APKSIZE")
    private long apkSize;//APK文件大小

    public String getApkCode() {
        return apkCode;
    }

    public void setApkCode(String apkCode) {
        this.apkCode = apkCode;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getApkDesc() {
        return apkDesc;
    }

    public void setApkDesc(String apkDesc) {
        this.apkDesc = apkDesc;
    }

    public String getApkType() {
        return apkType;
    }

    public void setApkType(String apkType) {
        this.apkType = apkType;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getApkLocalPath() {
        return apkLocalPath;
    }

    public void setApkLocalPath(String apkLocalPath) {
        this.apkLocalPath = apkLocalPath;
    }

    public String getApkDownLoad() {
        return apkDownLoad;
    }

    public void setApkDownLoad(String apkDownLoad) {
        this.apkDownLoad = apkDownLoad;
    }

    public String getApkImageName() {
        return apkImageName;
    }

    public void setApkImageName(String apkImageName) {
        this.apkImageName = apkImageName;
    }

    public String getApkImageLoacalPath() {
        return apkImageLoacalPath;
    }

    public void setApkImageLoacalPath(String apkImageLoacalPath) {
        this.apkImageLoacalPath = apkImageLoacalPath;
    }

    public String getApkImageDownLoad() {
        return apkImageDownLoad;
    }

    public void setApkImageDownLoad(String apkImageDownLoad) {
        this.apkImageDownLoad = apkImageDownLoad;
    }

    public boolean isHaveBestNew() {
        return haveBestNew;
    }

    public void setHaveBestNew(boolean haveBestNew) {
        this.haveBestNew = haveBestNew;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getApkFileName() {
        return apkFileName;
    }

    public void setApkFileName(String apkFileName) {
        this.apkFileName = apkFileName;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public boolean isDeleteDb() {
        return deleteDb;
    }

    public void setDeleteDb(boolean deleteDb) {
        this.deleteDb = deleteDb;
    }

    public long getApkSize() {
        return apkSize;
    }

    public void setApkSize(long apkSize) {
        this.apkSize = apkSize;
    }
}
