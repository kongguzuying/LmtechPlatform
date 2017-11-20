package com.lmtech.infrastructure.dao;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.model.AdminPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管理员权限Dao
 * Created by huang.jb on 2017-2-28.
 */
public interface AdminPermissionDao extends Dao<AdminPermission> {
    /**
     * 获取资源id通过资源类型
     * @param resourceType
     * @return
     */
    List<String> selectResourceIdByType(String resourceType);
}
