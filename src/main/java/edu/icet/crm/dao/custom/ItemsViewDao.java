package edu.icet.crm.dao.custom;

import edu.icet.crm.dao.SuperDao;
import edu.icet.crm.dto.ItemDto;

import java.util.List;

public interface ItemsViewDao extends SuperDao {
    public List<ItemDto> getAllItems();
    public boolean deleteItem(String itemId);
    public boolean updateItemStatus(String orderId, String status);
}
