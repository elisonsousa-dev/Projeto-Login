package br.sousa.Projeto.login.conexao.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;



@Getter
@Setter

@Component
public class UsuarioRequestDto {
    private Long id;
    private String token;
    private String nome;
    private String email;
    private String senha;


}
