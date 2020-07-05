package com.challenge.servico;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ServicoExtraiNumeros {

    private ServicoExtraiNumeros() {}
    
    public static BigDecimal somaNumerosDaAnotacaoSomar(Object objeto) {
        List<Field> campos = Arrays.asList(objeto.getClass().getDeclaredFields());
        return campos.stream()
            .filter(ServicoValidaCampo::ehCampoSomaValido)
            .map(campo -> pegaValorDoCampo(campo, objeto))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private static BigDecimal pegaValorDoCampo(Field field, Object objeto) {
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

    public static BigDecimal somaNumerosDaAnotacaoSubtrair(Object objeto) {
        List<Field> campos = Arrays.asList(objeto.getClass().getDeclaredFields());
        return campos.stream()
            .filter(ServicoValidaCampo::ehCampoSubtracaoValido)
            .map(campo -> pegaValorDoCampo(campo, objeto))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
