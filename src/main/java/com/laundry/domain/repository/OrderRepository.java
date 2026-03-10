package com.laundry.domain.repository;

import com.laundry.domain.entity.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order pedido);

    List<Order> listarTodos();

    Optional<Order> buscarPorId(String id);

    List<Order> buscarPorCpfCliente(String cpfCliente);
}