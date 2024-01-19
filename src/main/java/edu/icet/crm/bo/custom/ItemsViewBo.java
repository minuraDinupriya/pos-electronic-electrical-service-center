package edu.icet.crm.bo.custom;

import edu.icet.crm.bo.SuperBo;
import edu.icet.crm.dto.ItemDto;

import java.util.List;

public interface ItemsViewBo extends SuperBo {
    List<ItemDto> getAllItems();
    boolean deleteItem(String itemId);
    boolean updateItemStatus(String orderId, String newStatus);
}
