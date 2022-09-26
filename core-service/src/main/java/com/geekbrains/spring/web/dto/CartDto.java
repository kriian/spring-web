package com.geekbrains.spring.web.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartDto {
    private List<OrderItemDto> items;
    private  int totalPrice;

    public CartDto() {
        this.items = new ArrayList<>();
    }

    public void clear(){
        items.clear();
        totalPrice = 0;
    }
}
