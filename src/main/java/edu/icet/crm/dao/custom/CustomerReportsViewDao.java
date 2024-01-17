package edu.icet.crm.dao.custom;

import edu.icet.crm.dao.SuperDao;
import edu.icet.crm.dto.CustomerDto;

import java.util.List;

public interface CustomerReportsViewDao extends SuperDao {
    public List<CustomerDto> getAllCustomers();
    public boolean deleteCustomer(String id);
}
