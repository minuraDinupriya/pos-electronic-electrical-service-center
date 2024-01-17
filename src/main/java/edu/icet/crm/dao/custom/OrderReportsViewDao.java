package edu.icet.crm.dao.custom;

import edu.icet.crm.dao.SuperDao;
import edu.icet.crm.dto.OrderDto;
import java.util.List;

public interface OrderReportsViewDao extends SuperDao {
    public List<OrderDto> getAllOrders();
    public boolean deleteOrder(String orderId);
}
