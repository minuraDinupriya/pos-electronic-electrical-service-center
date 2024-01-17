package edu.icet.crm.bo.custom.impl;

import edu.icet.crm.bo.custom.ItemsViewBo;
import edu.icet.crm.dao.DaoFactory;
import edu.icet.crm.dao.custom.ItemsViewDao;
import edu.icet.crm.dao.util.DaoType;
import edu.icet.crm.dto.ItemDto;

import java.util.List;

public class ItemsViewBoImpl implements ItemsViewBo {
    ItemsViewDao itemsViewDao= DaoFactory.getInstance().getDao(DaoType.ITEMS_VIEW_DAO);


    @Override
    public List<ItemDto> getAllItems() {
         return itemsViewDao.getAllItems();
    }

    @Override
    public boolean deleteItem(String itemId) {
        return itemsViewDao.deleteItem(itemId);
    }

    @Override
    public boolean updateItemStatus(String orderId, String newStatus) {
        return itemsViewDao.updateItemStatus(orderId,newStatus);
    }
}
