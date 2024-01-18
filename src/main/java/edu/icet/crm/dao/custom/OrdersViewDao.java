package edu.icet.crm.dao.custom;
import edu.icet.crm.dao.SuperDao;
import edu.icet.crm.dto.OrderDto;

import java.util.List;
public interface OrdersViewDao extends SuperDao {
    public List<OrderDto> getOrdersViewDto();
    boolean updateOrderStatus(String orderId, String newStatus);
    boolean updateOrderTotal(String orderId, double total);
}
