package edu.icet.crm.dto;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CustomerReportsViewDto {
    private String customerId;
    private String name;
    private String contactNumber;
    private String emailAddress;
}
