package br.sousa.Projeto.login.conexao.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private Long id;
    @JsonIgnore
    private String token;

    private String nome;
    private String email;


}
