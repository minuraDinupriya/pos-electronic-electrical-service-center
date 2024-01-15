package edu.icet.crm.dao.custom;

import edu.icet.crm.dao.SuperDao;
import edu.icet.crm.dto.CustomerDto;
import edu.icet.crm.dto.PlaceOrderDto;

public interface PlaceOrderDao extends SuperDao {
    public void save(PlaceOrderDto placeOrderDto);
}
