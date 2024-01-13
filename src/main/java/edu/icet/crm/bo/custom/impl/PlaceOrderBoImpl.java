package edu.icet.crm.bo.custom.impl;

import edu.icet.crm.bo.custom.PlaceOrderBo;
import edu.icet.crm.dao.DaoFactory;
import edu.icet.crm.dao.util.DaoType;
import edu.icet.crm.dao.custom.PlaceOrderDao;
import edu.icet.crm.dto.CustomerDto;

public class PlaceOrderBoImpl implements PlaceOrderBo {
    PlaceOrderDao placeOrderDao= DaoFactory.getInstance().getDao(DaoType.PLACE_ORDER_DAO);
    public void save(){
        placeOrderDao.save(new CustomerDto(
                "cus1",
                "minura",
                "0705606683",
                "mranaweera793@gmail.com",
                "ord1",
                null,
                null
        ));
    }
}
