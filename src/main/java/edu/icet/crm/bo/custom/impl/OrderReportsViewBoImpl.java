package edu.icet.crm.bo.custom.impl;

import edu.icet.crm.bo.custom.OrderReportsViewBo;
import edu.icet.crm.dao.DaoFactory;
import edu.icet.crm.dao.custom.OrderReportsViewDao;
import edu.icet.crm.dao.util.DaoType;
import edu.icet.crm.dto.OrderDto;

import java.util.List;

public class OrderReportsViewBoImpl implements OrderReportsViewBo {
    OrderReportsViewDao orderReportsViewDao= DaoFactory.getInstance().getDao(DaoType.ORDER_REPORTS_VIEW_DAO);

    public List<OrderDto> getAllOrders(){
        return orderReportsViewDao.getAllOrders();
    }
}
