package com.agileengine.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
