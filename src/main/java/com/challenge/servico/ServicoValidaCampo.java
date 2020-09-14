package com.challenge.servico;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;

public class ServicoValidaCampo {
    
    public static final Class<? extends Annotation> ANOTACAO_SOMAR = Somar.class;
    public static final Class<? extends Annotation> ANOTACAO_SUBTRAIR = Subtrair.class;

    private ServicoValidaCampo() {}

    public static boolean ehCampoSomaValido(Field campo) {
        return ehBigDeciMal(campo) && ehAnotacaoValida(campo, ANOTACAO_SOMAR);
    }
    
    private static boolean ehBigDeciMal(Field campo) {
        return campo.getType().equals(BigDecimal.class);
    }
    
    private static boolean ehAnotacaoValida(Field campo, Class<? extends Annotation> classe) {
        return campo.isAnnotationPresent( classe);
    }

    public static boolean ehCampoSubtracaoValido(Field campo) {
        return ehBigDeciMal(campo) && ehAnotacaoValida(campo, ANOTACAO_SUBTRAIR);
    }

}
