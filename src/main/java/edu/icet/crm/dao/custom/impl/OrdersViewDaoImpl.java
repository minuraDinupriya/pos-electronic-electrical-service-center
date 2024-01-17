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
    public List<OrderDto> getOrdersViewDto() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "SELECT NEW edu.icet.crm.dto.OrderDto(o.orderId, o.orderStatus, o.customer.customerId, o.orderDate, o.note) " +
                    "FROM OrdersEntity o";
            Query<OrderDto> query = session.createQuery(hql, OrderDto.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateOrderStatus(String orderId, String newStatus) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                OrdersEntity ordersEntity = session.get(OrdersEntity.class, orderId);
                if (ordersEntity != null) {
                    ordersEntity.setOrderStatus(newStatus);
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