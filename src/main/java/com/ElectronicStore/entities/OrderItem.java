package com.ElectronicStore.entities;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name="order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private int quantity;

    private int totalPrice;

    @OneToOne
//    @JoinColumn(name="product_id"):::::::::::--------
//    for naming table column name that is going to make in
//    one to many mapping
    private Product product;

    @ManyToOne
    private Order order;
}
