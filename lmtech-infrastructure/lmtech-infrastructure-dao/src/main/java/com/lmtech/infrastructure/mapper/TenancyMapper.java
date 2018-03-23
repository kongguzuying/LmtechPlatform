package com.lmtech.infrastructure.mapper;

import com.lmtech.dao.LmtechBaseMapper;
import com.lmtech.infrastructure.model.Tenancy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * 租户Mapper
 * @author huang.jb
 */
@Service
public interface TenancyMapper extends LmtechBaseMapper<Tenancy> {
    Tenancy selectByCode(@Param("code") String code);
}
