package br.sousa.Projeto.login.conexao.dto;

import br.sousa.Projeto.login.conexao.model.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SenhaDTO {
    private String senhaAtual;
    private String novaSenha;


}
