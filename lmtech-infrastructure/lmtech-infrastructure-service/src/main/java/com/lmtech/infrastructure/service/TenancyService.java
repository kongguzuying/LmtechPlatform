package com.lmtech.infrastructure.service;

import com.lmtech.infrastructure.model.Tenancy;
import com.lmtech.service.DbManagerBase;

/**
 * 租户服务
 * @author huang.jb
 */
public interface TenancyService extends DbManagerBase<Tenancy> {
    /**
     * 通过code获取租户信息
     * @param code
     * @return
     */
    Tenancy getByCode(String code);
}
