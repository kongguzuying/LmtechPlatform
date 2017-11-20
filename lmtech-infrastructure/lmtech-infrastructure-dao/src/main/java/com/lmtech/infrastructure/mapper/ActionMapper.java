package com.lmtech.infrastructure.mapper;

import com.lmtech.dao.LmtechBaseMapper;
import com.lmtech.infrastructure.model.Action;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Action Mapper
 * Created by huang.jb on 2017-2-9.
 */
public interface ActionMapper extends LmtechBaseMapper<Action> {
    /**
     * 获取角色下的可操作动作
     * @param roleId
     * @param param
     * @return
     */
    List<Action> selectActionInRole(@Param("roleId") String roleId, @Param("param") Action param);

    /**
     * 获取不在角色下的可操作动作
     * @param roleId
     * @param param
     * @return
     */
    List<Action> selectActionNotInRole(@Param("roleId") String roleId, @Param("param") Action param);
}
