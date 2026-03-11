package com.laundry.application.usecases;

import com.laundry.domain.entity.Order;
import com.laundry.domain.enums.OrderStatus;
import com.laundry.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderStatusUsecase {

    private final OrderRepository repository;

    public UpdateOrderStatusUsecase(OrderRepository repository) {
        this.repository = repository;
    }

    public Order executar(String id, OrderStatus status) {

        Order order = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        order.setStatus(status);

        return repository.salvar(order);
    }
}