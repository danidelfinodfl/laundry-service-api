package com.laundry.adapters.output.memory;

import com.laundry.domain.entity.Order;
import com.laundry.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<String, Order> banco = new HashMap<>();

    @Override
    public Order save(Order Order) {

        if (Order.getId() == null) {
            Order.setId(UUID.randomUUID().toString());
        }

        banco.put(Order.getId(), Order);
        return Order;
    }

    @Override
    public List<Order> listarTodos() {
        return new ArrayList<>(banco.values());
    }

    @Override
    public Optional<Order> buscarPorId(String id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public List<Order> buscarPorCpfCliente(String cpfCliente) {

        List<Order> orders = new ArrayList<>();

        for (Order order : banco.values()) {

            if (order.getCpfCliente() != null &&
                    order.getCpfCliente().equals(cpfCliente)) {

                orders.add(order);
            }
        }

        return orders;
    }
}