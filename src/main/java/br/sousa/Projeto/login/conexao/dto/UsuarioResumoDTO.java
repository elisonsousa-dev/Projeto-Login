package br.sousa.Projeto.login.conexao.dto;


import br.sousa.Projeto.login.conexao.model.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UsuarioResumoDTO {
    private String nome;
    private String email;

    public UsuarioResumoDTO(){

    }

    public UsuarioResumoDTO(Usuario usuario){
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }
}
