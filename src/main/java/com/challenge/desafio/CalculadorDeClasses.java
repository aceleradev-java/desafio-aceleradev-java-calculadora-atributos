package com.challenge.desafio;

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
        BigDecimal total = campos.stream()
                                .filter(campo -> campo.getType().equals(BigDecimal.class) && campo.isAnnotationPresent(Somar.class))
                                .map( campo -> getValueFromField(campo, objeto))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;
    }

    private BigDecimal getValueFromField(Field field, Object objeto) {
        BigDecimal value = new BigDecimal(0);
        try {
            field.setAccessible(true);
            value = (BigDecimal) field.get(objeto);
        } catch (SecurityException e) {
            System.out.println("Campo não encontrado");
        } catch (IllegalArgumentException e) {
            System.out.println("Argumento inválido");
        } catch (IllegalAccessException e) {
            System.out.println("Você não tem permissão");
        }
        return value;
    }

    @Override
    public BigDecimal subtrair(Object objeto) {
        Object obj = objeto;
        List<java.lang.reflect.Field> campos = Arrays.asList(objeto.getClass().getDeclaredFields());
        BigDecimal total = campos.stream()
                                .filter(campo -> campo.getType().equals(BigDecimal.class) && campo.isAnnotationPresent(Subtrair.class))
                                .map( campo -> getValueFromField(campo, obj))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;
    }

    @Override
    public BigDecimal totalizar(Object objeto) {
        return somar(objeto).subtract(subtrair(objeto));
    }
}
