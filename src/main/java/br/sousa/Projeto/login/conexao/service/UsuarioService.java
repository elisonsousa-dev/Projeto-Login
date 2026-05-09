package br.sousa.Projeto.login.conexao.service;


import br.sousa.Projeto.login.conexao.dto.*;
import br.sousa.Projeto.login.conexao.model.Usuario;
import br.sousa.Projeto.login.conexao.repository.UsuarioRepository;
import br.sousa.Projeto.login.conexao.util.AuthUtil;
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
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private TokenUtil tokenUtil;



    public void cadastrarUsuario(UsuarioRequestDto usuario){
        boolean nomeValido = validador.validarNome(usuario.getNome());

        if(!nomeValido){
            throw new RuntimeException("O nome não pode ser vazio");
        }

        boolean emailValido = validador.validarEmail(usuario.getEmail());

        if(!emailValido){
            throw new RuntimeException("E-mail inválido!");
        }

         Usuario usuarioExt = repo.findByEmail(usuario.getEmail());

         if(usuarioExt != null){
             throw new RuntimeException("Este E-mail já está sendo utilizado");
         }

        boolean senhaValida = validador.validarSenha(usuario.getSenha());

        if(!senhaValida){
            throw new RuntimeException("A senha não pode ser vazia");
        }

        Usuario usuario1 = new Usuario();

        String hash =  SenhaUtil.gerarHash(usuario.getSenha());

        usuario1.setNome(usuario.getNome());
        usuario1.setEmail(usuario.getEmail());
        usuario1.setSenha(hash);
        usuario1.setRole(Usuario.Role.USER);

        repo.save(usuario1);

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

        return tokenUtil.gerarToken(usuario.getEmail(), usuario.getRole().name());

    }
    public void delete(String email){
        String tokenEmail = authUtil.getEmail(email);

        if(tokenEmail == null){
            throw new RuntimeException("Token inválido");
        }

        Usuario usuario = repo.findByEmail(tokenEmail);

        if(usuario == null){
            throw new RuntimeException("O usuário não foi encontrado");
        }

        repo.delete(usuario);

    }

    public LoginResponseDto buscarPorEmail(String email){
        Usuario usuario = repo.findByEmail(email);
        LoginResponseDto user = new LoginResponseDto();

        user.setId(usuario.getId());
        user.setNome(usuario.getNome());
        user.setEmail(usuario.getEmail());
        user.setRole(usuario.getRole().name());

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
        usuarios.setCargo(String.valueOf(usuario.getRole()));

        return usuarios;
    }
    public UsuarioResumoDTO atualizarDados(String token, UsuarioResumoDTO dados){
        Usuario usuario = repo.findByEmail(token);

        if(usuario == null){
           throw new RuntimeException("Usuário não encontrado!");
        }

        boolean nomeValido = validador.validarNome(dados.getNome());

        if(!nomeValido){
            throw new RuntimeException("Nome inválido");
        }

        boolean emailValido = validador.validarEmail(dados.getEmail());

        if(!emailValido){
            throw new RuntimeException("E-mail inválido");
        }

        Usuario email = repo.findByEmail(dados.getEmail());

        if(email != null){
            throw new RuntimeException("E-mail já está sendo utilizado!");
        }

            usuario.setNome(dados.getNome());
            usuario.setEmail(dados.getEmail());

            repo.save(usuario);

            return new UsuarioResumoDTO(usuario);

    }
    public void atualizarSenha(String token, AtualizarSenhaDTO dados){
        Usuario usuario = repo.findByEmail(token);

        if(usuario == null){
            throw  new RuntimeException("Usuário não encontrado");

        }

      if(!BCrypt.checkpw(dados.getSenhaAtual(), usuario.getSenha())){
          throw new RuntimeException("Senha atual incorreta!");

      }

      if(BCrypt.checkpw(dados.getNovaSenha(), usuario.getSenha())){
          throw new RuntimeException("A Sua nova senha não pode ser igual a atual");
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
    public void setCargo(String token,String email, String role){
        String tokenEmail = authUtil.getEmail(token);

        if(tokenEmail == null){
            throw new RuntimeException("Token inválido");
        }

        Usuario solicitante = repo.findByEmail(tokenEmail);

        if(solicitante == null){
           throw new RuntimeException("Seu token expirou");
        }

        String validarRole = authUtil.getRole(token);

       if(!Usuario.Role.CEO.name().equals(validarRole)){
           throw new RuntimeException("Usuário não autorizado!");
       }

       boolean emailValido = validador.validarEmail(email);

       if(!emailValido){
           throw new RuntimeException("Email invalido, Digite o formato correto");
       }

       Usuario alvo = repo.findByEmail(email);

       if(alvo == null){
           throw new RuntimeException("O usuário não foi encontrado");
       }

       if(role.isEmpty()){
           throw new RuntimeException("O cargo não pode ser vazio");
       }
         role = role.toUpperCase();

       if(alvo.getRole() == Usuario.Role.valueOf(role)){
           throw new RuntimeException("Usuário já possui esse cargo");
       }

       alvo.setRole(Usuario.Role.valueOf(role));
       repo.save(alvo);

    }

    public void admin(String header){
        String email = authUtil.getEmail(header);

        if(email == null){
            throw new RuntimeException("token inválido");
        }

        Usuario usuario = repo.findByEmail(email);

        if(usuario == null){
            throw new RuntimeException("O usuário não foi encontrado");
        }

        String role = authUtil.getRole(header);

        if(!Usuario.Role.ADMIN.name().equals(role) && !Usuario.Role.CEO.name().equals(role)){
            throw new RuntimeException("Acesso negado");
        }
    }

    public List<UsuarioResponseDTO> lista(){
        return repo.findAll().stream().map(usuario -> {
            UsuarioResponseDTO dto = new UsuarioResponseDTO();
            dto.setId(usuario.getId());
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());
            dto.setCargo(String.valueOf(usuario.getRole()));
            return dto;
        } ).toList();
    }

}
