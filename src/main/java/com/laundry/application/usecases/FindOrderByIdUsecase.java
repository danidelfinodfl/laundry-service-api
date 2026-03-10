package com.laundry.application.usecases;

import com.laundry.domain.entity.Order;
import com.laundry.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindOrderByIdUsecase {

    private final OrderRepository repository;

    public FindOrderByIdUsecase(OrderRepository repository) {
        this.repository = repository;
    }

    public Optional<Order> executar(String id) {
        return repository.buscarPorId(id);
    }
}