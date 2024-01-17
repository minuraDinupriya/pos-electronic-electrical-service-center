package edu.icet.crm.bo.custom;

import edu.icet.crm.bo.SuperBo;
import edu.icet.crm.dto.ItemDto;

import java.util.List;

public interface ItemsViewBo extends SuperBo {
    public List<ItemDto> getAllItems();
    public boolean deleteItem(String itemId);
    public boolean updateItemStatus(String orderId, String newStatus);
}
