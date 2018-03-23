package com.ea.card.trade.service.impl;

import com.ea.card.trade.dao.OrderDao;
import com.ea.card.trade.model.Order;
import com.ea.card.trade.service.OrderService;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends AbstractDbManagerBaseImpl<Order> implements OrderService {

    @Autowired
    private OrderDao orderDao;

    public Dao getDao() {
        return orderDao;
    }
}
