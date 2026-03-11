package com.laundry.application.usecases;

import com.laundry.adapters.output.viacep.ViaCepClient;
import com.laundry.domain.entity.Order;
import com.laundry.domain.enums.OrderStatus;
import com.laundry.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateOrderUsecaseTest {

    private OrderRepository orderRepository;
    private CreateOrderUsecase usecase;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        ViaCepClient viaCepClient = new ViaCepClient();
        usecase = new CreateOrderUsecase(orderRepository, viaCepClient);
    }

    @Test
    void deveCriarPedidoComStatusRecebido() {

        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setCpfCliente("12345678900");
        order.setCepCliente("09960010");
        order.setNomeCliente("Daniel");
        order.setDescricaoPedido("3 camisas");

        when(orderRepository.salvar(any())).thenReturn(order);

        Order resultado = usecase.executar(order);

        assertNotNull(resultado);
        assertEquals(OrderStatus.RECEBIDO, resultado.getStatus());
    }
    @Test
    void naoDeveCriarPedidoSemCpf() {

        Order order = new Order();
        order.setDescricaoPedido("3 camisas");

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usecase.executar(order)
        );

        assertEquals("CPF do cliente é obrigatório", exception.getMessage());
    }
    @Test
    void deveSalvarPedidoNoRepositorio() {

        Order order = new Order();
        order.setCpfCliente("12345678900");
        order.setNomeCliente("João");
        order.setCepCliente("09960010");
        order.setDescricaoPedido("3 camisas");

        when(orderRepository.salvar(any())).thenReturn(order);

        Order resultado = usecase.executar(order);

        assertNotNull(resultado);
        verify(orderRepository).salvar(any());
    }
}