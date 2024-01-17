// OrderBean.java
package edu.icet.crm.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class OrderBean {
    private String orderId;
    private String status;
    private String customerId;
    private String orderDate;
    private String note;
}
