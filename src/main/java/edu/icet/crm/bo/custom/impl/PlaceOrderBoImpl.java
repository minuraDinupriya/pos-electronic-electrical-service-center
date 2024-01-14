package edu.icet.crm.bo.custom.impl;

import edu.icet.crm.bo.custom.PlaceOrderBo;
import edu.icet.crm.dao.DaoFactory;
import edu.icet.crm.dao.util.DaoType;
import edu.icet.crm.dao.custom.PlaceOrderDao;
import edu.icet.crm.dto.CustomerDto;
import edu.icet.crm.dto.tm.PlaceOrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlaceOrderBoImpl implements PlaceOrderBo {
    PlaceOrderDao placeOrderDao= DaoFactory.getInstance().getDao(DaoType.PLACE_ORDER_DAO);

    public void save(){
        placeOrderDao.save(new CustomerDto(
                "cus3",
                "minura",
                "0705606683",
                "mranaweera793@gmail.com",
                "ord3",
                null,
                null
        ));
    }
}
