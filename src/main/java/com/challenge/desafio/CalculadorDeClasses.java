package com.challenge.desafio;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;
import com.challenge.interfaces.Calculavel;


public class CalculadorDeClasses implements Calculavel {

    @Override
    public BigDecimal somar(Object objeto) {
        List<Field> campos = Arrays.asList(objeto.getClass().getDeclaredFields());
        return campos.stream()
            .filter(CalculadorDeClasses::ehCampoSomaValido)
            .map( campo -> pegaValorDoCampo(campo, objeto))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static boolean ehCampoSomaValido(Field campo) {
        return ehBigDeciMal(campo) && ehAnotacaoValida(campo, Somar.class);
    }
    
    private static boolean ehBigDeciMal(Field campo) {
        return campo.getType().equals(BigDecimal.class);
    }
    
    private static boolean ehAnotacaoValida(Field campo, Class<? extends Annotation> classe) {
        return campo.isAnnotationPresent( classe);
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
            .filter(CalculadorDeClasses::ehCampoSubtracaoValido)
            .map( campo -> pegaValorDoCampo(campo, objeto))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static boolean ehCampoSubtracaoValido(Field campo) {
        return ehBigDeciMal(campo) && ehAnotacaoValida(campo, Subtrair.class);
    }

    @Override
    public BigDecimal totalizar(Object objeto) {
        return somar(objeto).subtract(subtrair(objeto));
    }
}
