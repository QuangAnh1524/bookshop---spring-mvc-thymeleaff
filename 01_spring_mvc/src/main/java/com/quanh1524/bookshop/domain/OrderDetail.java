package com.quanh1524.bookshop.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long quantity;
    private double price;

    //order_id: long
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    //product_id: long
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
