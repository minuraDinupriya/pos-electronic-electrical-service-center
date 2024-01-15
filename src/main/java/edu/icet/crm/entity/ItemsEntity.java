package edu.icet.crm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
@NoArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "Item")
public class ItemsEntity {

    @Id
    @Column(name = "item_id", nullable = false)
    private String itemId;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "status")
    private String status;

//    @Column(name = "qty")
//    private int qty;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrdersEntity order;

    public ItemsEntity(String itemId, String name, String category, String status) {//, int qty
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.status = status;
//        this.qty = qty;
    }
}