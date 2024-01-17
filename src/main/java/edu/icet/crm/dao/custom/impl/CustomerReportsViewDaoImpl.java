package edu.icet.crm.dao.custom.impl;


import edu.icet.crm.dao.custom.CustomerReportsViewDao;
import edu.icet.crm.dao.util.HibernateUtil;
import edu.icet.crm.dto.CustomerDto;
import edu.icet.crm.entity.CustomerEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerReportsViewDaoImpl implements CustomerReportsViewDao {
    @Override
    public List<CustomerDto> getAllCustomers() {
        try (Session session=HibernateUtil.getSession()) {
            String hql = "SELECT new edu.icet.crm.dto.CustomerDto(c.customerId, c.customerName, c.contactNumber, c.emailAddress) " +
                    "FROM CustomerEntity c";
            Query<CustomerDto> query = session.createQuery(hql, CustomerDto.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();

            CustomerEntity customer = session.get(CustomerEntity.class, customerId);

            if (customer != null) {
                session.delete(customer);
                transaction.commit();
                return true;
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return false;
    }
}
