package edu.icet.crm.bo.custom;

import edu.icet.crm.bo.SuperBo;
import edu.icet.crm.dto.PlaceOrderDto;

public interface PlaceOrderBo extends SuperBo {
    public void save(PlaceOrderDto placeOrderDto);
    public String getLastOrderId();
    public int getLstItemId();
    public String getLastCustomerId();
}
