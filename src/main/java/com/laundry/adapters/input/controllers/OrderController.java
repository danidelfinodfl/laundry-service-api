package com.laundry.adapters.input.controllers;

import com.laundry.application.usecases.CreateOrderUsecase;
import com.laundry.application.usecases.FindOrderByClientUsecase;
import com.laundry.application.usecases.ListOrderUsecase;
import com.laundry.application.usecases.FindOrderByIdUsecase;
import com.laundry.application.usecases.UpdateOrderStatusUsecase;
import com.laundry.domain.entity.Order;
import com.laundry.domain.enums.OrderStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class OrderController {

    private final CreateOrderUsecase criarPedido;
    private final ListOrderUsecase listarPedidos;
    private final FindOrderByClientUsecase buscarPorCliente;
    private final FindOrderByIdUsecase buscarPorId;
    private final UpdateOrderStatusUsecase atualizarStatus;

    public OrderController(
            CreateOrderUsecase criarPedido,
            ListOrderUsecase listarPedidos,
            FindOrderByIdUsecase buscarPorId,
            FindOrderByClientUsecase buscarPorCliente, UpdateOrderStatusUsecase atualizarStatus) {

        this.criarPedido = criarPedido;
        this.listarPedidos = listarPedidos;
        this.buscarPorCliente = buscarPorCliente;
        this.buscarPorId = buscarPorId;
        this.atualizarStatus = atualizarStatus;
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
    
    @GetMapping("/{id}")
    public Optional<Order> buscarPorId(@PathVariable String id) {

        return buscarPorId.executar(id);
        
    }

    @PatchMapping("/{id}/status")
    public Order atualizarStatus(
            @PathVariable String id,
            @RequestParam OrderStatus status) {

        return atualizarStatus.executar(id, status);
    }

    @DeleteMapping("/{id}")
    public Order cancelarPedido(@PathVariable String id) {

        return atualizarStatus.executar(id, OrderStatus.CANCELADO);
    }
}