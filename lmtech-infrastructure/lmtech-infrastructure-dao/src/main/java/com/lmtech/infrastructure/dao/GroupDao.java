package com.lmtech.infrastructure.dao;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.model.Group;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 群组Dao
 * Created by huang.jb on 2017-2-28.
 */
public interface GroupDao extends Dao<Group> {
    /**
     * 查询用户下的群组
     * @param userId
     * @param param
     * @return
     */
    List<Group> selectGroupInUser(String userId, Group param);

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
    List<Group> selectChildrenGroup(String parentId);
}
