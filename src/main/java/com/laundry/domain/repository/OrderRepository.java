package com.laundry.domain.repository;

import com.laundry.domain.entity.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order salvar(Order pedido);

    Order save(Order Order);

    List<Order> listarTodos();

    Optional<Order> buscarPorId(String id);

    List<Order> buscarPorCpfCliente(String cpfCliente);
}