package com.lmtech.infrastructure.mapper;

import com.lmtech.dao.LmtechBaseMapper;
import com.lmtech.infrastructure.model.AdminPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管理员权限Mapper
 * Created by huang.jb on 2017-2-9.
 */
public interface AdminPermissionMapper extends LmtechBaseMapper<AdminPermission> {
    /**
     * 获取资源id通过资源类型
     * @param resourceType
     * @return
     */
    List<String> selectResourceIdByType(@Param("resourceType") String resourceType);
}
