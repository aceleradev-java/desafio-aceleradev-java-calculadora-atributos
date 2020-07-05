package com.challenge.desafio;

import java.math.BigDecimal;

import com.challenge.interfaces.Calculavel;
import com.challenge.servico.ServicoExtraiNumeros;


public class CalculadorDeClasses implements Calculavel {

    @Override
    public BigDecimal somar(Object objeto) {
        return ServicoExtraiNumeros.somaNumerosDaAnotacaoSomar(objeto);
    }

    @Override
    public BigDecimal subtrair(Object objeto) {
        return ServicoExtraiNumeros.somaNumerosDaAnotacaoSubtrair(objeto);
    }

    @Override
    public BigDecimal totalizar(Object objeto) {
        return somar(objeto).subtract(subtrair(objeto));
    }
}
