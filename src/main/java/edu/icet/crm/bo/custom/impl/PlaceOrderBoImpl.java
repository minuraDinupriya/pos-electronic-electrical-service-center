package edu.icet.crm.bo.custom.impl;

import edu.icet.crm.bo.custom.PlaceOrderBo;
import edu.icet.crm.dao.DaoFactory;
import edu.icet.crm.dao.util.DaoType;
import edu.icet.crm.dao.custom.PlaceOrderDao;
import edu.icet.crm.dto.CustomerDto;
import edu.icet.crm.dto.OrderDetailsDto;
import edu.icet.crm.dto.OrderDto;
import edu.icet.crm.dto.PlaceOrderDto;
import edu.icet.crm.dto.tm.PlaceOrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBoImpl implements PlaceOrderBo {
    PlaceOrderDao placeOrderDao= DaoFactory.getInstance().getDao(DaoType.PLACE_ORDER_DAO);

    public void save(){
        List<OrderDetailsDto> orderDetailsDtoList=new ArrayList<>();
        orderDetailsDtoList.add(new OrderDetailsDto(
                "itm2",
                "Tv",
                "electrical"
        ));
        placeOrderDao.save(new PlaceOrderDto(

                "cus20",
                "minura",
                "0705606683",
                "mranaweera793@gmail.com",
                "ord21",
                "1",
                "note",
                orderDetailsDtoList
        ));
    }
}
