package edu.icet.crm.dto;

import java.util.List;

public class OrderDto {
    private String orderId;
    private String date;
    private String note;
    private List<OrderDetailsDto> orderDetailsDtoList;
    private String customerId;
}
