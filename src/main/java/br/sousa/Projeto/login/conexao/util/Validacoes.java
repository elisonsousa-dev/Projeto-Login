package br.sousa.Projeto.login.conexao.util;

import org.springframework.stereotype.Component;

@Component
public class Validacoes {
    public boolean validarEmail(String email){
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
    public boolean validarSenha(String senha){
        if(senha.isEmpty()){
            return false;
        }
        return true;
    }
    public boolean validarNome(String nome){
        if(nome.isEmpty()){
            return false;
        }else {
            return true;
        }
    }
}
