package br.sousa.Projeto.login.conexao.controller;

import br.sousa.Projeto.login.conexao.dto.SenhaDTO;
import br.sousa.Projeto.login.conexao.dto.UsuarioDto;
import br.sousa.Projeto.login.conexao.dto.UsuarioRaquastDTO;
import br.sousa.Projeto.login.conexao.dto.UsuarioResponseDTO;
import br.sousa.Projeto.login.conexao.service.UsuarioService;

import br.sousa.Projeto.login.conexao.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;
    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/cadastro")
     public ResponseEntity<String> cadastrar(@RequestBody UsuarioDto usuario){
        try {
            service.cadastrar(usuario);

            return ResponseEntity.ok("Usuario cadastrado");

        }catch (RuntimeException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
     }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDto request){

        try {
            service.login(request.getEmail(), request.getSenha());
            return ResponseEntity.ok(service.buscarPorEmail(request.getEmail()));

        }catch (RuntimeException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }


    }
     @GetMapping("/admin")
     public ResponseEntity<?> heard(@RequestHeader("Authorization") String hearder){

        String email = authUtil.validarHearder(hearder);

        if(email == null){
            return ResponseEntity.status(401).body("Usuário não autorizado!");
        }
        return ResponseEntity.ok(service.lista());
     }
       @GetMapping("/me")
     public ResponseEntity<?> usuario(@RequestHeader("Authorization") String hearder){
        String user = authUtil.validarHearder(hearder);
           Map<String,Object> response = new LinkedHashMap<>();

        if(user == null){
            return ResponseEntity.status(401).body("Usuario não encontrado");
        }
          try {
              UsuarioResponseDTO usuario = service.buscarPorToken(user);

              response.put("mensagem", "Sucesso!");
              response.put("dados", usuario);
              return ResponseEntity.ok(response);

          }catch (RuntimeException e){
              return ResponseEntity.status(400).body(e.getMessage());
          }
     }
     @PutMapping("/me")
     public ResponseEntity<?> atualizar(@RequestHeader("Authorization") String hearder, @RequestBody UsuarioRaquastDTO dados){
        String token = authUtil.validarHearder(hearder);

            try {
                UsuarioRaquastDTO usuarioAtualizar = service.atualizar(token, dados);
                return ResponseEntity.ok(usuarioAtualizar);

            }catch (RuntimeException e){

                return ResponseEntity.status(400).body(e.getMessage());
            }
     }

     @PutMapping("/senha")
     public ResponseEntity<?> atualizarSenha(@RequestHeader("Authorization") String hearder, @RequestBody SenhaDTO dados){

        String token = authUtil.validarHearder(hearder);

              try {
                  service.atualizarSenha(token, dados);
                  return ResponseEntity.ok("Senha Atualizada com sucesso");

              }catch (RuntimeException e){
                  return ResponseEntity.status(400).body(e.getMessage());
              }
     }


     @DeleteMapping("/me")
     public ResponseEntity<String> delete(@RequestHeader("Authorization") String hearder){
        String token = authUtil.validarHearder(hearder);

        if(token == null){
            return ResponseEntity.status(401).body("O usuário não existe!");
        }
        service.delete(token);
             return ResponseEntity.ok("Conta excluida!");
     }

}
