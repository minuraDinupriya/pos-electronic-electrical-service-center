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
                "itm5",
                "Tv",
                "electrical"
        ));

//        orderDetailsDtoList.add(new OrderDetailsDto(
//                        "itm6",
//                        "Tv",
//                        "electrical"
//        ));

        String orderId;
        if (placeOrderDao.getLastOrderId()==null){
            orderId="ord1";
        }else {

            int num = Integer.parseInt(placeOrderDao.getLastOrderId().split("[d]")[1]);
            orderId = String.format("ord%d", ++num);
        }

        placeOrderDao.save(new PlaceOrderDto(

                "cus26",
                "minura",
                "mranaweera793@gmail.com",
                "0705606683",
                orderId,
                "1",
                "note",
                orderDetailsDtoList
        ));
    }
}
