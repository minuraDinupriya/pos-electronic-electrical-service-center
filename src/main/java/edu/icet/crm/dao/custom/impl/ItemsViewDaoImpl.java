package edu.icet.crm.dao.custom.impl;

import edu.icet.crm.dao.custom.ItemsViewDao;
import edu.icet.crm.dao.util.HibernateUtil;
import edu.icet.crm.dto.ItemDto;
import edu.icet.crm.entity.ItemsEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ItemsViewDaoImpl implements ItemsViewDao {

    public List<ItemDto> getAllItems() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "SELECT new edu.icet.crm.dto.ItemDto(i.itemId, i.status, i.category, i.name, i.order.orderId) " +
                    "FROM ItemsEntity i";
            Query<ItemDto> query = session.createQuery(hql, ItemDto.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteItem(String itemId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();

            ItemsEntity item = session.get(ItemsEntity.class, itemId);

            if (item != null) {
                session.delete(item);
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

    public boolean updateItemStatus(String itemId, String newStatus) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                ItemsEntity itemsEntity = session.get(ItemsEntity.class, itemId);
                if (itemsEntity != null) {
                    itemsEntity.setStatus(newStatus);
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
