package edu.icet.crm.bo.custom;
import edu.icet.crm.bo.SuperBo;
import edu.icet.crm.dto.OrderDto;

import java.util.List;
public interface OrdersViewBo extends SuperBo {
    public List<OrderDto> getOrdersViewDto();
    public boolean updateOrder(OrderDto updatedDto);

}
