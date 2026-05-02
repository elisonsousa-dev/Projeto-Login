package br.sousa.Projeto.login.conexao.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {
    private String mensagem;
    private int status;

    public ResponseDTO(String mensagem, int status){
        this.mensagem = mensagem;
        this.status = status;
    }

}
