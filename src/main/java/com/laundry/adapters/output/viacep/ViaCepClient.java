package com.laundry.adapters.output.viacep;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ViaCepClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public EnderecoViaCepResponse buscarEndereco(String cep) {

        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        return restTemplate.getForObject(url, EnderecoViaCepResponse.class);
    }
}
