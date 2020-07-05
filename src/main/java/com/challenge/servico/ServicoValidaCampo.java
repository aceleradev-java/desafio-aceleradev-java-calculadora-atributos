package com.challenge.servico;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;

public class ServicoValidaCampo {

    private ServicoValidaCampo() {}

    public static boolean ehCampoSomaValido(Field campo) {
        return ehBigDeciMal(campo) && ehAnotacaoValida(campo, Somar.class);
    }
    
    private static boolean ehBigDeciMal(Field campo) {
        return campo.getType().equals(BigDecimal.class);
    }
    
    private static boolean ehAnotacaoValida(Field campo, Class<? extends Annotation> classe) {
        return campo.isAnnotationPresent( classe);
    }

    public static boolean ehCampoSubtracaoValido(Field campo) {
        return ehBigDeciMal(campo) && ehAnotacaoValida(campo, Subtrair.class);
    }

}
