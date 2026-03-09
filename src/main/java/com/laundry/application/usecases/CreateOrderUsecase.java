package com.laundry.application.usecases;

import com.laundry.domain.entity.Order;
import com.laundry.domain.repository.OrderRepository;

public class CreateOrderUsecase {

    private final OrderRepository repository;

    public CreateOrderUsecase(OrderRepository repository) {
        this.repository = repository;
    }

    public Order execute(Order order) {

        order.setStatus("Recebido");

        return repository.save(order);
    }
}