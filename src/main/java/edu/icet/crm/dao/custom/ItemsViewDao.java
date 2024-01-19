package edu.icet.crm.dao.custom;

import edu.icet.crm.dao.SuperDao;
import edu.icet.crm.dto.ItemDto;
import edu.icet.crm.entity.ItemsEntity;

import java.util.List;

public interface ItemsViewDao extends SuperDao {
    public List<ItemsEntity> getAllItems();
    public boolean deleteItem(String itemId);
    public boolean updateItemStatus(String orderId, String status);
    public String getCustomerEmailByOrderId(String orderId);
    public String getOrderIdByItemId(String itemId);
    int getOrderItemCountByStatus(String orderId, String status);
    int getTotalItemCountByOrderId(String orderId);
}

