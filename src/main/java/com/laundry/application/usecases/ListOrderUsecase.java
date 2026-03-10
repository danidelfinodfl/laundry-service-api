package com.laundry.application.usecases;

import com.laundry.domain.entity.Order;
import com.laundry.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListOrderUsecase {

    private final OrderRepository repository;

    public ListOrderUsecase(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> executar() {
        return repository.listarTodos();
    }

    public List<Order> executar(String cpfCliente) {

        return repository.buscarPorCpfCliente(cpfCliente);
    }
}