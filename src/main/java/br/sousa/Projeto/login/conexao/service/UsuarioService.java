package br.sousa.Projeto.login.conexao.service;


import br.sousa.Projeto.login.conexao.dto.*;
import br.sousa.Projeto.login.conexao.model.Usuario;
import br.sousa.Projeto.login.conexao.repository.UsuarioRepository;
import br.sousa.Projeto.login.conexao.util.SenhaUtil;
import br.sousa.Projeto.login.conexao.util.TokenUtil;
import br.sousa.Projeto.login.conexao.util.Validacoes;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repo;
    @Autowired
    private Validacoes validador;


    public boolean cadastrar(UsuarioDto usuario){

        boolean nomeValido = validador.validarNome(usuario.getNome());

        if(!nomeValido){
            throw new RuntimeException("O nome não pode ser vazio");
        }

        boolean emailValido = validador.validarEmail(usuario.getEmail());

        if(!emailValido){
            throw new RuntimeException("E-mail inválido!");
        }

        boolean senhaValida = validador.validarSenha(usuario.getSenha());

        if(!senhaValida){
            throw new RuntimeException("A senha não pode ser vazia");
        }

        Usuario usuario1 = new Usuario();

        usuario1.setNome(usuario.getNome());
        usuario1.setEmail(usuario.getEmail());
        usuario1.setSenha(usuario.getSenha());

      String hash =  SenhaUtil.gerarHash(usuario.getSenha());
        usuario1.setSenha(hash);

        repo.save(usuario1);

       return true;
    }

    public String login(String email, String senha){
        boolean emailValido = validador.validarEmail(email);

        if(!emailValido){
            throw new RuntimeException("E-mail inválido!");
        }

        boolean senhaValido = validador.validarSenha(senha);

        if(!senhaValido){
            throw new RuntimeException("Senha inválida");
        }

        Usuario usuario = repo.findByEmail(email);

        if(usuario == null){
            throw new RuntimeException("Usuário não encontrado!");
        }

       boolean senhaCorreta = SenhaUtil.verificar(senha, usuario.getSenha());

        if(!senhaCorreta){
            throw new RuntimeException("Senha incorreta!");
        }

        String token = TokenUtil.gerarToken(usuario.getEmail());
        usuario.setToken(token);

        return token;
    }
    public void delete(String email) {
        Usuario usuario = repo.findByEmail(email);

        if(usuario != null){

            repo.delete(usuario);
        }

    }
    public ResponseDto buscarPorEmail(String email){
        Usuario usuario = repo.findByEmail(email);
        ResponseDto user = new ResponseDto();

        user.setToken(usuario.getToken());
        user.setId(usuario.getId());
        user.setNome(usuario.getNome());
        user.setEmail(usuario.getEmail());

        return user;

    }
    public UsuarioResponseDTO buscarPorToken(String token){

        Usuario usuario = repo.findByEmail(token);

        if(usuario == null){
            throw new RuntimeException("Usuário não encontrado!");
        }
        UsuarioResponseDTO usuarios = new UsuarioResponseDTO();

        usuarios.setId(usuario.getId());
        usuarios.setNome(usuario.getNome());
        usuarios.setEmail(usuario.getEmail());

        return usuarios;
    }
    public UsuarioRaquastDTO atualizar( String token, UsuarioRaquastDTO dados){
        Usuario usuario = repo.findByEmail(token);

        if(usuario == null){
           throw new RuntimeException("Usuário não encontrado!");
        }
        boolean emailValido = validador.validarEmail(dados.getEmail());

        if(!emailValido){
            throw new RuntimeException("E-mail inválido");
        }

        boolean nomeValido = validador.validarNome(dados.getNome());

        if(!nomeValido){
            throw new RuntimeException("Nome inválido");
        }

        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail());

        repo.save(usuario);

        return new UsuarioRaquastDTO(usuario);

    }
    public void atualizarSenha(String token, SenhaDTO dados){
        Usuario usuario = repo.findByEmail(token);

        if(usuario == null){
            throw  new RuntimeException("Usuário não encontrado");

        }
      if(!BCrypt.checkpw(dados.getSenhaAtual(), usuario.getSenha())){
          throw new RuntimeException("Senha atual incorreta!");

      }

        if(dados.getNovaSenha() == null || dados.getNovaSenha().isEmpty()){
            throw new RuntimeException("Senha inválida!");

        }

        String novaSenha = SenhaUtil.gerarHash(dados.getNovaSenha());
        usuario.setSenha(novaSenha);

        dados.setNovaSenha(usuario.getSenha());
        dados.setSenhaAtual(usuario.getSenha());


        repo.save(usuario);


    }

    public List<ResponseDto> lista(){
        return repo.findAll().stream().map(usuario -> {
            ResponseDto dto = new ResponseDto();
            dto.setId(usuario.getId());
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());
            return dto;
        } ).toList();
    }
}
