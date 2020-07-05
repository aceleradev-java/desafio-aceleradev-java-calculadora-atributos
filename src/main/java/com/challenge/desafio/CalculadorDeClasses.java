package com.challenge.desafio;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.challenge.interfaces.Calculavel;
import com.challenge.servico.ServicoValidaCampo;


public class CalculadorDeClasses implements Calculavel {

    @Override
    public BigDecimal somar(Object objeto) {
        List<Field> campos = Arrays.asList(objeto.getClass().getDeclaredFields());
        return campos.stream()
            .filter(ServicoValidaCampo::ehCampoSomaValido)
            .map( campo -> pegaValorDoCampo(campo, objeto))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal pegaValorDoCampo(Field field, Object objeto) {
        try {
            field.setAccessible(true);
            return (BigDecimal) field.get(objeto);
        } catch (SecurityException e) {
            System.out.println("Campo não encontrado");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Argumento inválido");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Você não tem permissão");
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal subtrair(Object objeto) {
        List<Field> campos = Arrays.asList(objeto.getClass().getDeclaredFields());
        return campos.stream()
            .filter(ServicoValidaCampo::ehCampoSubtracaoValido)
            .map( campo -> pegaValorDoCampo(campo, objeto))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal totalizar(Object objeto) {
        return somar(objeto).subtract(subtrair(objeto));
    }
}
