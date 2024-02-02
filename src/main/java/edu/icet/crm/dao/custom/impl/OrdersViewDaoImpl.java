package edu.icet.crm.dao.custom.impl;

import edu.icet.crm.dao.custom.OrdersViewDao;
import edu.icet.crm.dao.util.HibernateUtil;
import edu.icet.crm.dto.OrderDto;

import java.util.List;

import edu.icet.crm.entity.OrdersEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
public class OrdersViewDaoImpl implements OrdersViewDao {
    @Override
    public List<OrdersEntity> getOrdersViewDto() {
        try (Session session = HibernateUtil.getSession()) {
            Query query = session.createQuery("FROM OrdersEntity");
            List<OrdersEntity> list = query.list();
            session.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateOrder(OrdersEntity updatedEntity) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                OrdersEntity existingEntity = session.get(OrdersEntity.class, updatedEntity.getOrderId());
                if (existingEntity != null) {

                    if (updatedEntity.getOrderStatus() != null) {
                        existingEntity.setOrderStatus(updatedEntity.getOrderStatus());
                    }

                    if (updatedEntity.getTotal() != null) {
                        existingEntity.setTotal(updatedEntity.getTotal()+(existingEntity.getTotal()==null?0:existingEntity.getTotal()));
                    }

                    transaction.commit();
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
                return false;
            }
        }
    }

}