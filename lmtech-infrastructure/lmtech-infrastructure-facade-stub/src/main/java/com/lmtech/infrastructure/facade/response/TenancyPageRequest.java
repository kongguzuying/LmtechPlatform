package com.lmtech.infrastructure.facade.response;

import com.lmtech.facade.request.PageRequest;
import com.lmtech.infrastructure.facade.dto.TenancyQueryParam;

/**
 * 租户分页查询请求
 * @author huang.jb
 */
public class TenancyPageRequest extends PageRequest<TenancyQueryParam> {
    public void validate() {

    }
}
