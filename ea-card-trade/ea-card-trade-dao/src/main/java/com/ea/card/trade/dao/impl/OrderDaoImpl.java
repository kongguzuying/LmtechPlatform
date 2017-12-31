package com.ea.card.trade.dao.impl;

import com.ea.card.trade.dao.OrderDao;
import com.ea.card.trade.mapper.OrderMapper;
import com.ea.card.trade.model.Order;
import com.lmtech.dao.MyBatisDaoBase;
import org.springframework.stereotype.Service;

@Service
public class OrderDaoImpl extends MyBatisDaoBase<OrderMapper, Order> implements OrderDao {
}
