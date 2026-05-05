package br.sousa.Projeto.login.conexao.util;

import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
    // verificar se heard existe e tem o padrao correto
    public String getToken(String hearder) {
        if (hearder == null || !hearder.startsWith("Bearer ")) {
            return null;
        }

       return hearder.replace("Bearer ","").trim();
    }
    public String getRole(String header){
        String token = getToken(header);
        if(token == null){
            return null;
        }
        return TokenUtil.validarRole(token);
    }
    public String getEmail(String header){
        String token = getToken(header);
        if(token == null){
            return null;
        }
        return TokenUtil.validarToken(token);
    }

}
