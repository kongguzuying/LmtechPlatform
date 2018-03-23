package com.lmtech.admin.common.config;

/**
 *  配置信息
 * Created by huang.jb on 2016-7-14.
 */
public class ConfigInfo {
    public static final int DIALECT_MYSQL = 0;
    public static final int DIALECT_ORACLE = 1;

    /**
     * 分页大小
     */
    public static int PAGE_SIZE = 12;
    /**
     * 数据库Dialect
     */
    public static int DB_DIALECT = DIALECT_MYSQL;
    /**
     * 主键生成器地编号
     */
    public static long WORKER_ID = 1;
    /**
     * 数据中心编号
     */
    public static long DATACENTER_ID = 2;
    /**
     * 超级管理员用户角色Id
     */
    public static String SUPER_ADMIN_ROLE_ID="";
    /**
     * 系统管理员角色Id
     */
    public static String ADMIN_ROLE_ID="";
    /**
     * 文件最大限制MB
     */
    public static long MAX_FILE_SIZE = 50;
    /**
     * 文件服务器地址
     */
    public static String FILE_SERVER_URL = "";
}
