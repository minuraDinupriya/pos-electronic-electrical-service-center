package edu.icet.crm.dao.custom;

import edu.icet.crm.dao.SuperDao;
import edu.icet.crm.dto.CustomerReportsViewDto;
import java.util.List;

public interface CustomerReportsViewDao extends SuperDao {
    public List<CustomerReportsViewDto> getAllCustomers();
    public boolean deleteCustomer(String id);
}
