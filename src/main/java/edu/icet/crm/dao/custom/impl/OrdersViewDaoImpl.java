package edu.icet.crm.dao.custom.impl;

import edu.icet.crm.dao.custom.OrdersViewDao;
import edu.icet.crm.dao.util.HibernateUtil;
import edu.icet.crm.dto.OrdersViewDto;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
public class OrdersViewDaoImpl implements OrdersViewDao {
    @Override
    public List<OrdersViewDto> getOrdersViewDto() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "SELECT NEW edu.icet.crm.dto.OrdersViewDto(o.orderId, o.orderStatus, o.customer.customerId, o.orderDate, o.note) " +
                    "FROM OrdersEntity o";
            Query<OrdersViewDto> query = session.createQuery(hql, OrdersViewDto.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately in a production environment
            return null;
        }
    }
}