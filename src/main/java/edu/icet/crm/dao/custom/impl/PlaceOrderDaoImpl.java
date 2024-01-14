package edu.icet.crm.dao.custom.impl;

import edu.icet.crm.dao.custom.PlaceOrderDao;
import edu.icet.crm.dao.util.HibernateUtil;
import edu.icet.crm.dto.CustomerDto;
import edu.icet.crm.entity.CustomerEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PlaceOrderDaoImpl implements PlaceOrderDao {
    public void save(CustomerDto customerDto){
        Session session= HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.save(new CustomerEntity(
                customerDto.getCustomerId(),
                customerDto.getCustomerName(),
                customerDto.getEmail(),
                customerDto.getContactNumber()
        ));

        transaction.commit();
        session.close();
    }
}
