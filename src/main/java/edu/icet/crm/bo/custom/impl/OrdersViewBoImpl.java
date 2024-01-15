package edu.icet.crm.bo.custom.impl;

import edu.icet.crm.bo.custom.OrdersViewBo;
import edu.icet.crm.dao.DaoFactory;
import edu.icet.crm.dao.custom.OrdersViewDao;
import edu.icet.crm.dao.util.DaoType;
import edu.icet.crm.dto.OrdersViewDto;

import java.util.List;

public class OrdersViewBoImpl implements OrdersViewBo {
    OrdersViewDao orderViewDao= DaoFactory.getInstance().getDao(DaoType.ORDERS_VIEW_DAO);
    @Override
    public List<OrdersViewDto> getOrdersViewDto() {
        return orderViewDao.getOrdersViewDto();
    }
}
