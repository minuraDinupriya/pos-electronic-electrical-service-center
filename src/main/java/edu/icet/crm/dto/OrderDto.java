package edu.icet.crm.dto;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OrderDto {
    private String orderId;
    private String date;
    private String note;
    private List<OrderDetailsDto> orderDetailsDtoList;
    private String customerId;
}
