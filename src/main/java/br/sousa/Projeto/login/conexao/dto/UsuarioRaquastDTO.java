package br.sousa.Projeto.login.conexao.dto;


import br.sousa.Projeto.login.conexao.model.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UsuarioRaquastDTO {
    private String nome;
    private String email;

    public UsuarioRaquastDTO(){

    }

    public UsuarioRaquastDTO(Usuario usuario){
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }
}
