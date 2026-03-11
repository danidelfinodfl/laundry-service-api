package com.laundry.config;


public class CepNaoEncontradoException extends RuntimeException {

    public CepNaoEncontradoException(String cep) {
        super("CEP não encontrado. Verifique se está correto ou informe o endereço manualmente");
    }
}