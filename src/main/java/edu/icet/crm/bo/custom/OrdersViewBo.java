package edu.icet.crm.bo.custom;
import edu.icet.crm.bo.SuperBo;
import edu.icet.crm.dto.OrdersViewDto;

import java.util.List;
public interface OrdersViewBo extends SuperBo {
    public List<OrdersViewDto> getOrdersViewDto();
    boolean updateOrderStatus(String orderId, String newStatus);
}
