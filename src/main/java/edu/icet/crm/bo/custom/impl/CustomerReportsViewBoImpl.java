package edu.icet.crm.bo.custom.impl;

import edu.icet.crm.bo.custom.CustomerReportsViewBo;
import edu.icet.crm.dao.DaoFactory;
import edu.icet.crm.dao.custom.CustomerReportsViewDao;
import edu.icet.crm.dao.util.DaoType;
import edu.icet.crm.dto.CustomerDto;

import java.util.List;

public class CustomerReportsViewBoImpl implements CustomerReportsViewBo {
    CustomerReportsViewDao customerReportsViewDao= DaoFactory.getInstance().getDao(DaoType.CUSTOMER_REPORTS_VIEW_DAO);
    public List<CustomerDto> getCustomers(){
        return customerReportsViewDao.getAllCustomers();
    }

    @Override
    public boolean deleteCustomer(String id) {
        return customerReportsViewDao.deleteCustomer(id);
    }
}
