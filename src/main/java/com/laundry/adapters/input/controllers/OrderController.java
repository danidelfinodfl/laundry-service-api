package com.laundry.adapters.input.controllers;

import com.laundry.application.usecases.CreateOrderUsecase;
import com.laundry.domain.entity.Order;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class OrderController {

    private final CreateOrderUsecase createOrderUsecase;

    public OrderController(CreateOrderUsecase createOrderUsecase) {
        this.createOrderUsecase = createOrderUsecase;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return createOrderUsecase.executar(order);
    }
}