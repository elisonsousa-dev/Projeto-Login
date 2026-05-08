package br.sousa.Projeto.login.conexao.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
    @Autowired
    private TokenUtil tokenUtil;

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

        return tokenUtil.validarRole(token);
    }
    public String getEmail(String header){
        String token = getToken(header);
        if(token == null){
            return null;
        }
        return tokenUtil.validarToken(token);
    }

}
