package com.lmtech.infrastructure.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lmtech.dao.LmtechBaseMapper;
import com.lmtech.infrastructure.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper
 * Created by huang.jb on 2017-2-9.
 */
public interface UserMapper extends LmtechBaseMapper<User> {
    /**
     * 获取分组下的用户
     * @param groupId
     * @param param
     * @return
     */
    List<User> selectUserInGroup(@Param("groupId") String groupId, @Param("param") User param, @Param("excludeUserIds") List<String> excludeUserIds);

    /**
     * 获取不在分组下的用户
     * @param groupId
     * @param param
     * @return
     */
    List<User> selectUserNotInGroup(@Param("groupId") String groupId, @Param("param") User param, @Param("excludeUserIds") List<String> excludeUserIds);

    /**
     * 获取角色下的用户
     * @param roleId
     * @param param
     * @return
     */
    List<User> selectUserInRole(@Param("roleId") String roleId, @Param("param") User param, @Param("excludeUserIds") List<String> excludeUserIds);

    /**
     * 获取不在角色下的用户
     * @param roleId
     * @param param
     * @return
     */
    List<User> selectUserNotInRole(@Param("roleId") String roleId, @Param("param") User param, @Param("excludeUserIds") List<String> excludeUserIds);

    /**
     * 删除用户关联数据
     * @param userId
     */
    void deleteUserRelation(@Param("userId") String userId);

    /**
     * 通过帐户获取用户信息
     * @param account
     * @return
     */
    User selectUserByAccount(@Param("account") String account);

    /**
     * 通过关键字获取用户数据
     * @param key
     * @return
     */
    List<User> selectUserByKey(@Param("key") String key);
}
