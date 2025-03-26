package com.agileengine.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {
    private int order_id;
    private int product_id;
}
