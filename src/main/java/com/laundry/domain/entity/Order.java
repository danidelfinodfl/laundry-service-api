package com.laundry.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String id;
    private String nomeCliente;
    private String cepCliente;
    private String endereco;
    private String status;
}