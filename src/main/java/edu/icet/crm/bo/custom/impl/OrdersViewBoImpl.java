package edu.icet.crm.bo.custom.impl;

import edu.icet.crm.bo.custom.OrdersViewBo;
import edu.icet.crm.dao.DaoFactory;
import edu.icet.crm.dao.custom.OrdersViewDao;
import edu.icet.crm.dao.util.DaoType;
import edu.icet.crm.dto.OrderDto;

import java.util.List;

public class OrdersViewBoImpl implements OrdersViewBo {
    OrdersViewDao orderViewDao= DaoFactory.getInstance().getDao(DaoType.ORDERS_VIEW_DAO);
    @Override
    public List<OrderDto> getOrdersViewDto() {
        return orderViewDao.getOrdersViewDto();
    }
    public boolean updateOrderStatus(String orderId, String newStatus){
        return orderViewDao.updateOrderStatus(orderId,newStatus);
    }
}
