package com.laundry.application.usecases;

import com.laundry.domain.entity.Order;
import com.laundry.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindOrderByClientUsecase {

    private final OrderRepository repository;

    public FindOrderByClientUsecase(OrderRepository repositorio) {
        this.repository = repositorio;
    }

    public List<Order> executar(String cpfCliente) {

        return repository.buscarPorCpfCliente(cpfCliente);
    }
}