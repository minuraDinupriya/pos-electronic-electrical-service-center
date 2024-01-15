package edu.icet.crm.dao.custom;
import edu.icet.crm.dao.SuperDao;
import edu.icet.crm.dto.OrdersViewDto;
import edu.icet.crm.entity.OrdersEntity;

import java.util.List;
public interface OrdersViewDao extends SuperDao {
    public List<OrdersViewDto> getOrdersViewDto();
}
