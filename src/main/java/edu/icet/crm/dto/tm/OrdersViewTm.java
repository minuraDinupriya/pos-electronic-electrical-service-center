package edu.icet.crm.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OrdersViewTm {
    private String orderId;
    private String status;
    private String customerId;
    private String orderDate;
    private String note;
    private JFXButton returnButton;
    private JFXButton closeOrderButton;

    public OrdersViewTm(String orderId, String status, String customerId, String orderDate, String note, JFXButton returnButton, JFXButton closeOrderButton) {
        this.orderId = orderId;
        this.status = status;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.note = note;
        this.returnButton = returnButton;
        this.closeOrderButton = closeOrderButton;
    }

    private String total;
}
