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

    public String getLastOrderId(){

        if (placeOrderDao.getLastOrderId()==null){
            return "ord1";
        }else {

            int num = Integer.parseInt(placeOrderDao.getLastOrderId().split("[d]")[1]);
            return String.format("ord%d", ++num);
        }
    }

    public int getLstItemId(){
        if (placeOrderDao.getLastItemId()==null){
            return 0;
        }else {
            return Integer.parseInt(placeOrderDao.getLastItemId().split("[m]")[1]);
        }
    }

    public String getLastCustomerId(){
        if (placeOrderDao.getLastCustomerId()==null){
            return "ord1";
        }else {

            int num = Integer.parseInt(placeOrderDao.getLastCustomerId().split("[s]")[1]);
            return String.format("cus%d", ++num);
        }
    }

//    public void save(){
//
//        List<OrderDetailsDto> orderDetailsDtoList=new ArrayList<>();
//        orderDetailsDtoList.add(new OrderDetailsDto(
//                "itm5",
//                "Tv",
//                "electrical"
//        ));
//
////        orderDetailsDtoList.add(new OrderDetailsDto(
////                        "itm6",
////                        "Tv",
////                        "electrical"
////        ));
//
//        for (OrderDetailsDto orderDetailsDto:orderDetailsDtoList){
//
//        }
//
//
//        placeOrderDao.save(new PlaceOrderDto(
//
//                "cus26",
//                "minura",
//                "mranaweera793@gmail.com",
//                "0705606683",
//                "ad",
//                "1",
//                "note",
//                orderDetailsDtoList
//        ));
//    }

    @Override
    public void save(PlaceOrderDto placeOrderDto) {
        placeOrderDao.save(placeOrderDto);
    }
}
