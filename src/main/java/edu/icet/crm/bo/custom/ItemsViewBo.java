package edu.icet.crm.bo.custom;

import edu.icet.crm.bo.SuperBo;
import edu.icet.crm.dto.ItemsViewDto;

import java.util.List;

public interface ItemsViewBo extends SuperBo {
    public List<ItemsViewDto> getAllItems();
    public boolean deleteItem(String itemId);
}
