package com.laundry.adapters.output.viacep;

import lombok.Data;

@Data
public class EnderecoViaCepResponse {

    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private Boolean erro;
}