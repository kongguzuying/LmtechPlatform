package com.lmtech.infrastructure.dao;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.model.Action;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Action Dao
 * Created by huang.jb on 2017-2-28.
 */
public interface ActionDao extends Dao<Action> {
    /**
     * 获取角色下的可操作动作
     * @param roleId
     * @param param
     * @return
     */
    List<Action> selectActionInRole(String roleId, Action param);

    /**
     * 获取不在角色下的可操作动作
     * @param roleId
     * @param param
     * @return
     */
    List<Action> selectActionNotInRole(String roleId, Action param);
}
