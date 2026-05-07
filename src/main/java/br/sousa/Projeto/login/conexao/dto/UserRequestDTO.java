package br.sousa.Projeto.login.conexao.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UserRequestDTO {
    private String email;
    private String role;
}
