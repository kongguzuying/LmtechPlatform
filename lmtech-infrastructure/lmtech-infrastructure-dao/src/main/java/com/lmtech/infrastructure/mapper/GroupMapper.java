package com.lmtech.infrastructure.mapper;

import com.lmtech.dao.LmtechBaseMapper;
import com.lmtech.infrastructure.model.Group;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分组Mapper
 * Created by huang.jb on 2017-2-8.
 */
public interface GroupMapper extends LmtechBaseMapper<Group> {
    /**
     * 查询用户下的分组
     * @param userId
     * @param param
     * @return
     */
    List<Group> selectGroupInUser(@Param("userId") String userId, @Param("param") Group param);

    /**
     * 查询根群组
     * @return
     */
    List<Group> selectRootGroup();

    /**
     * 查询子群组
     * @param parentId
     * @return
     */
    List<Group> selectChildrenGroup(@Param("parentId") String parentId);
}
