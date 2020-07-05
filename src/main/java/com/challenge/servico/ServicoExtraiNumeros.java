package com.challenge.servico;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ServicoExtraiNumeros {

    private ServicoExtraiNumeros() {}
    
    public static BigDecimal somarNumerosDaAnotacaoSomar(Object objeto) {
        return somarNumerosDosCampos(objeto, ServicoValidaCampo::ehCampoSomaValido);
    }
    
    private static BigDecimal somarNumerosDosCampos(Object objeto, Predicate<Field> servicoValidaCampo) {
        List<Field> campos = pegaTodosOsCampos(objeto);
        return campos.stream()
            .filter(servicoValidaCampo::test)
            .map(campo -> pegaValorDoCampo(campo, objeto))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private static List<Field> pegaTodosOsCampos(Object objeto) {
        return Arrays.asList(objeto.getClass().getDeclaredFields());
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

    public static BigDecimal somarNumerosDaAnotacaoSubtrair(Object objeto) {
        return somarNumerosDosCampos(objeto, ServicoValidaCampo::ehCampoSubtracaoValido);
    }
}
