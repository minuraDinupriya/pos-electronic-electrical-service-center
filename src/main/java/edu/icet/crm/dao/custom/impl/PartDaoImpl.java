package edu.icet.crm.dao.custom.impl;

import edu.icet.crm.dao.custom.PartDao;
import edu.icet.crm.dao.util.HibernateUtil;
import edu.icet.crm.dto.PartDto;
import edu.icet.crm.entity.ItemsEntity;
import edu.icet.crm.entity.PartEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PartDaoImpl implements PartDao {
    @Override
    public boolean savePart(PartDto partDto) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                ItemsEntity itemEntity = session.get(ItemsEntity.class, partDto.getItemId());

                PartEntity partEntity = new PartEntity(
                        partDto.getName(),
                        partDto.getQuantity(),
                        partDto.getPrice()
                );

                partEntity.setItem(itemEntity);

                session.save(partEntity);

                transaction.commit();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
                return false;
            }
        }
    }
}
