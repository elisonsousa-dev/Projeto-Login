package br.sousa.Projeto.login.conexao.util;

import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
    // verificar se heard existe e tem o padrao correto
    public String validarHearder(String hearder) {
        if (hearder == null || !hearder.startsWith("Bearer")) {
            return null;
        }
        // Extrair o token
        String token = hearder.replace("Bearer", "");
         // Validar o token e retornar o email ou null
        return TokenUtil.validarToken(token);
    }
}
