package br.com.yagovcb.rhitmohospedeapi.application.policies;

import java.util.regex.Pattern;

public class PoliticaValidacaoSenha {

    public static boolean validaForcaSenha(String senha){
        final int MINIMUM_SIZE = 8;
        Pattern patternLowerCase = Pattern.compile(".*[a-z].*");
        Pattern patternUpperCase = Pattern.compile(".*[A-Z].*");
        Pattern patternNumberCase = Pattern.compile(".*[0-9].*");
        Pattern patternSpecialCharactersCase = Pattern.compile(".*[!@#$%^&*].*");

        return senha != null && senha.trim().length() >= MINIMUM_SIZE
                && patternLowerCase.matcher(senha).matches()
                && patternUpperCase.matcher(senha).matches()
                && patternNumberCase.matcher(senha).matches()
                && patternSpecialCharactersCase.matcher(senha).matches();
    }

    public static boolean validaSenhaIgual(String senha, String senhaReescrita){
        return senha.equalsIgnoreCase(senhaReescrita);
    }
}
