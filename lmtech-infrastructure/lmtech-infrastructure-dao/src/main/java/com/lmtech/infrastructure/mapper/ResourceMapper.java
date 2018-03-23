package com.lmtech.infrastructure.mapper;

import com.lmtech.dao.LmtechBaseMapper;
import com.lmtech.infrastructure.model.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源Mapper
 * Created by huang.jb on 2017-4-20.
 */
public interface ResourceMapper extends LmtechBaseMapper<Resource> {
    /**
     * 搜索角色下的资源
     * @param roleId
     * @param param
     * @return
     */
    List<Resource> selectResourceInRole(@Param("roleId") String roleId, @Param("param") Resource param);

    /**
     * 搜索不在角色下的资源
     * @param roleId
     * @param param
     * @return
     */
    List<Resource> selectResourceNotInRole(@Param("roleId") String roleId, @Param("param") Resource param);

    /**
     * 删除资源关联关系
     * @param resourceId
     */
    void deleteResourceRelation(@Param("resourceId") String resourceId);
}
