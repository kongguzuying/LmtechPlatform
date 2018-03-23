package com.lmtech.infrastructure.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.infrastructure.dao.ActionDao;
import com.lmtech.infrastructure.mapper.ActionMapper;
import com.lmtech.infrastructure.model.Action;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huang.jb on 2017-2-28.
 */
@Service
public class ActionDaoImpl extends MyBatisDaoBase<ActionMapper, Action> implements ActionDao {

    @Override
    public List<Action> selectActionInRole(String roleId, Action param) {
        return baseMapper.selectActionInRole(roleId, param);
    }

    @Override
    public List<Action> selectActionNotInRole(String roleId, Action param) {
        return baseMapper.selectActionNotInRole(roleId, param);
    }
}
