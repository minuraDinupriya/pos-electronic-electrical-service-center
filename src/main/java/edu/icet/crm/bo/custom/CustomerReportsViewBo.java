package edu.icet.crm.bo.custom;

import edu.icet.crm.bo.SuperBo;
import edu.icet.crm.dto.CustomerReportsViewDto;

import java.util.List;

public interface CustomerReportsViewBo extends SuperBo {
    public List<CustomerReportsViewDto> getCustomers();
    public boolean deleteCustomer(String id);
}
