package com.laundry.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;

    private String nomeCliente;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String cpfCliente;

    private String cepCliente;

    private String descricaoPedido;

    private String rua;

    private String numero;

    private String complemento;

    private String cidade;

    private String estado;

    private String status;
}