package edu.icet.crm.bo.custom;
import edu.icet.crm.bo.SuperBo;
import edu.icet.crm.dto.OrderDto;

import java.util.List;
public interface OrdersViewBo extends SuperBo {
    public List<OrderDto> getOrdersViewDto();
    boolean updateOrderStatus(String orderId, String newStatus);
    boolean updateOrderTotal(String orderId, double total);
}
