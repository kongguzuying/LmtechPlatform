package com.ea.card.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameter;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ea.card.crm.model.IntegralRule;
import com.lmtech.dao.LmtechBaseMapper;


public interface IntegralRuleMapper extends LmtechBaseMapper<IntegralRule> {
	
	List<IntegralRule> selectByType(@Param("type")String type);

}
