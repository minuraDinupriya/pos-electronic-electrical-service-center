package edu.icet.crm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
@NoArgsConstructor
@Getter

@Setter
@Entity
@Table(name = "Orders")
public class OrdersEntity {

    @Id
    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "order_date")
    private String orderDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)//customer_id widiyat wens krnn
    private CustomerEntity customer;

    @Column(name = "note")
    private String note;

    @Column(name = "order_status")
    private String orderStatus;//added later

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<ItemsEntity> items;

    public OrdersEntity(String orderId, String orderDate, String note, String orderStatus) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.note = note;
        this.orderStatus = orderStatus;
    }
}