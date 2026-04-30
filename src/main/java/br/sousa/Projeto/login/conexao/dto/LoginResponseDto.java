package br.sousa.Projeto.login.conexao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private Long id;
    private String token;
    private String nome;
    private String email;


}
