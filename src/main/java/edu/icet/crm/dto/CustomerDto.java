package edu.icet.crm.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CustomerDto {
    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("customer_name")
    private String name;

    @JsonProperty("contact_number")
    private String contactNumber;

    @JsonProperty("email_address")
    private String emailAddress;
}
