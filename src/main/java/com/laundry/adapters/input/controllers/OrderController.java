package com.laundry.adapters.input.controllers;

import com.laundry.application.usecases.CreateOrderUsecase;
import com.laundry.application.usecases.FindOrderByClientUsecase;
import com.laundry.application.usecases.ListOrderUsecase;
import com.laundry.domain.entity.Order;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class OrderController {

    private final CreateOrderUsecase criarPedido;
    private final ListOrderUsecase listarPedidos;
    private final FindOrderByClientUsecase buscarPorCliente;

    public OrderController(
            CreateOrderUsecase criarPedido,
            ListOrderUsecase listarPedidos,
            FindOrderByClientUsecase buscarPorCliente) {

        this.criarPedido = criarPedido;
        this.listarPedidos = listarPedidos;
        this.buscarPorCliente = buscarPorCliente;
    }

    @PostMapping
    public Order criar(@RequestBody Order order) {
        return criarPedido.executar(order);
    }

    @GetMapping
    public List<Order> listar() {
        return listarPedidos.executar();
    }

    @GetMapping("/cliente/{cpfCliente}")
    public List<Order> buscarPorCliente(@PathVariable String cpfCliente) {
        return buscarPorCliente.executar(cpfCliente);
    }
}