package br.sousa.Projeto.login.conexao.controller;

import br.sousa.Projeto.login.conexao.dto.ResponseDto;
import br.sousa.Projeto.login.conexao.dto.UsuarioDto;
import br.sousa.Projeto.login.conexao.model.Usuario;
import br.sousa.Projeto.login.conexao.service.UsuarioService;

import br.sousa.Projeto.login.conexao.util.Validacoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
private UsuarioService service;
    @Autowired
private Validacoes validador;

boolean senhaValida;
boolean emailValido;

    @PostMapping("/cadastro")
     public ResponseEntity<String> cadastrar(@RequestBody UsuarioDto usuario){

        emailValido = validador.validarEmail(usuario.getEmail());

        if(!emailValido){
            return ResponseEntity.ok("E-mail invalido!");
        }

        boolean nomeValido = validador.validarNome(usuario.getNome());

        if(!nomeValido){
            return ResponseEntity.ok("Nome não pode ser vazio!");
        }

        senhaValida = validador.validarSenha(usuario.getSenha());

        if(!senhaValida){
            return ResponseEntity.badRequest().body("Senha não pode ser vazia!");
        }

        service.cadastrar(usuario);

        return ResponseEntity.ok("Usuario cadastrado");

     }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDto request){

        emailValido = validador.validarEmail(request.getEmail());
        if(!emailValido){
            return ResponseEntity.badRequest().body("E-mail invalido!");
        }

        senhaValida = validador.validarSenha(request.getSenha());

        if(!senhaValida){
            return ResponseEntity.badRequest().body("Senha invalida!");
        }

        Usuario ok = service.login(request.getEmail(), request.getSenha());

        if(ok == null){
            return ResponseEntity.badRequest().body("E-mail ou senha invalido! Tente novamente.");
        }else {
            return ResponseEntity.ok("Logado!");
        }
    }
     @GetMapping("/list")
     public List<ResponseDto> list(){
        return service.lista();
     }

     @DeleteMapping("/{id}")
     public ResponseEntity<String> delete(@PathVariable Long id){
          service.delete(id);
             return ResponseEntity.ok("Conta excluida!");
     }

}
