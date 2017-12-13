package com.lmtech.auth.facade.stub;

import com.lmtech.auth.facade.dto.TokenLogQueryParam;
import com.lmtech.auth.facade.request.TokenValidateRequest;
import com.lmtech.auth.facade.response.TokenDataResponse;
import com.lmtech.auth.facade.response.TokenValidateResponse;
import com.lmtech.facade.request.PageRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.PageDataResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Token相关入口
 * Created by huang.jb on 2017-1-22.
 */
@FeignClient(name = "lmtech-auth")
@RequestMapping(value = "/token")
public interface TokenFacade {

    /**
     * 校验Token有效性
     * @param request
     * @return
     */
    @RequestMapping(value = "/validateToken", method = RequestMethod.POST)
    TokenValidateResponse validateToken(TokenValidateRequest request);

    /**
     * 解析token数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/parseToken", method = RequestMethod.POST)
    TokenDataResponse parseToken(StringRequest request);

    /**
     * 获取Token记录日志
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTokenLogs", method = RequestMethod.POST)
    PageDataResponse getTokenLogs(PageRequest<TokenLogQueryParam, Object> request);
}
