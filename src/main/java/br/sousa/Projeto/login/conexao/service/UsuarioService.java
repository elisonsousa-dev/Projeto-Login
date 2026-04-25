package br.sousa.Projeto.login.conexao.service;


import br.sousa.Projeto.login.conexao.dto.ResponseDto;
import br.sousa.Projeto.login.conexao.dto.UsuarioDto;
import br.sousa.Projeto.login.conexao.model.Usuario;
import br.sousa.Projeto.login.conexao.repository.UsuarioRepository;
import br.sousa.Projeto.login.conexao.util.SenhaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repo;

    public boolean cadastrar(UsuarioDto usuario){
        Usuario usuario1 = new Usuario();

        usuario1.setNome(usuario.getNome());
        usuario1.setEmail(usuario.getEmail());
        usuario1.setSenha(usuario.getSenha());

      String hash =  SenhaUtil.gerarHash(usuario.getSenha());
        usuario1.setSenha(hash);

       Usuario cadastrado = repo.save(usuario1);

       if(cadastrado == null){
           return false;
       }else {
           return true;
       }
    }
    public Usuario login(String email, String senha){

        Usuario usuario = repo.findByEmail(email);

        if(usuario == null){
            return null;
        }

     boolean senhaValida = SenhaUtil.verificar(senha, usuario.getSenha());

        if(senhaValida){

            return usuario;
        }
            return null;
    }
    public void delete(long id) {
        repo.deleteById(id);

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
