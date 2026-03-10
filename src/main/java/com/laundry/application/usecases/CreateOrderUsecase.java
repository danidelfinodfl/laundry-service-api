package com.laundry.application.usecases;

import com.laundry.adapters.output.viacep.EnderecoViaCepResponse;
import com.laundry.adapters.output.viacep.ViaCepClient;
import com.laundry.config.CepNaoEncontradoException;
import com.laundry.domain.entity.Order;
import com.laundry.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateOrderUsecase {

    private final OrderRepository repository;
    private final ViaCepClient viaCepClient;

    public CreateOrderUsecase(OrderRepository repository,
                              ViaCepClient viaCepClient) {
        this.repository = repository;
        this.viaCepClient = viaCepClient;
    }

    public Order executar(Order order) {

        if (order.getDescricaoPedido() == null || order.getDescricaoPedido().isBlank()) {
            throw new RuntimeException("Descrição do pedido é obrigatória.");
        }
        if (order.getCpfCliente() == null || order.getCpfCliente().isBlank()) {
            throw new RuntimeException("CPF do cliente é obrigatório");
        }

        EnderecoViaCepResponse endereco = null;

        try {
            endereco = viaCepClient.buscarEndereco(order.getCepCliente());
        } catch (Exception e) {
            //se api falhar seguimos para endereço manual
        }

        boolean cepInvalido =
                endereco == null ||
                        Boolean.TRUE.equals(endereco.getErro()) ||
                        endereco.getLogradouro() == null;

        if (cepInvalido) {

            //verifica se usuário enviou endereço manual
            if (order.getRua() == null ||
                    order.getCidade() == null ||
                    order.getEstado() == null) {

                throw new CepNaoEncontradoException(
                        order.getCepCliente() +
                                ". Informe rua, cidade e estado manualmente."
                );
            }

        } else {

            //preencher automaticamente se não veio manual
            if (order.getRua() == null || order.getRua().isBlank()) {
                order.setRua(endereco.getLogradouro());
            }

            if (order.getCidade() == null || order.getCidade().isBlank()) {
                order.setCidade(endereco.getLocalidade());
            }

            if (order.getEstado() == null || order.getEstado().isBlank()) {
                order.setEstado(endereco.getUf());
            }
        }

        order.setStatus("RECEBIDO");

        return repository.save(order);
    }
}