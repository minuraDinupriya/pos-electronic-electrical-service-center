package edu.icet.crm.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ItemsViewDto {

    private String itemId;
    private String status;
    private String category;
    private String name;
    private String orderId;
}