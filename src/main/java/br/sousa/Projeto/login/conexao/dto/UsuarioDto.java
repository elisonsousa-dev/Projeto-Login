package br.sousa.Projeto.login.conexao.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {
    private Long id;
    private String nome;
    private String email;
    private String senha;

}
