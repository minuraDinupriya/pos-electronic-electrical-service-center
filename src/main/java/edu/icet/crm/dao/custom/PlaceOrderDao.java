package edu.icet.crm.dao.custom;

import edu.icet.crm.dao.SuperDao;
import edu.icet.crm.dto.CustomerDto;

public interface PlaceOrderDao extends SuperDao {
    public void save(CustomerDto customerDto);
}
