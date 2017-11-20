package com.ea.card.crm.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ea.card.crm.model.IntegralDO;
import com.ea.card.crm.model.IntegralSignLog;
import com.lmtech.dao.LmtechBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IntegralSignLogMapper extends LmtechBaseMapper<IntegralSignLog> {
    List<IntegralSignLog> getIntegralSignLogList(@Param("userId") String userId, @Param("integralSource") int integralSource);
    Map<String,Object> getIntegralSignLog(@Param("userId") String userId,@Param("integralSource") int integralSource);
    int updateIntegralSignLog(IntegralDO integralDO);
    int countByUserId(@Param("userId") String userId,@Param("integralSource") int integralSource);

    int updateIsSignLog(@Param("date") Date date);

    int updateSignCountLog(@Param("date") Date date);
}
