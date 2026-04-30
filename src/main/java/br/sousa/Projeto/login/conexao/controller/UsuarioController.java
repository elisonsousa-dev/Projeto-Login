package br.sousa.Projeto.login.conexao.controller;

import br.sousa.Projeto.login.conexao.dto.AtualizarSenhaDTO;
import br.sousa.Projeto.login.conexao.dto.UsuarioRequestDto;
import br.sousa.Projeto.login.conexao.dto.UsuarioResumoDTO;
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

    Map<String,Object> response = new LinkedHashMap<>();

    @PostMapping("/cadastro")
     public ResponseEntity<?> cadastrar(@RequestBody UsuarioRequestDto usuario){

        try {
            service.cadastrarUsuario(usuario);
            response.put("Mensagem","Usuario cadastrado");

            return ResponseEntity.ok(response);

        }catch (RuntimeException e){
            response.put("Mensagem", e.getMessage());

            return ResponseEntity.status(400).body(response);

        }
     }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioRequestDto request){

        try {
            service.login(request.getEmail(), request.getSenha());

            return ResponseEntity.ok(service.buscarPorEmail(request.getEmail()));


        }catch (RuntimeException e){
            response.put("Mensagem", e.getMessage());
            return ResponseEntity.status(400).body(request);

        }

    }
     @GetMapping("/admin")
     public ResponseEntity<?> heard(@RequestHeader("Authorization") String hearder){

        String email = authUtil.validarHearder(hearder);

        if(email == null){
            response.put("Mensagem", "Usuário não autorizado!");
            return ResponseEntity.status(401).body(response);
        }
        return ResponseEntity.ok(service.lista());
     }

       @GetMapping("/me")
     public ResponseEntity<?> usuario(@RequestHeader("Authorization") String hearder){
        String user = authUtil.validarHearder(hearder);


          try {
              UsuarioResponseDTO usuario = service.buscarPorToken(user);

              response.put("mensagem", "Sucesso!");
              response.put("dados", usuario);
              return ResponseEntity.ok(response);

          }catch (RuntimeException e){
              response.put("Mensagem:", e.getMessage());

              return ResponseEntity.status(400).body(response);

          }
     }
     @PutMapping("/me")
     public ResponseEntity<?> atualizarDados(@RequestHeader("Authorization") String hearder, @RequestBody UsuarioResumoDTO dados){
        String token = authUtil.validarHearder(hearder);

            try {
                UsuarioResumoDTO usuarioAtualizar = service.atualizarDados(token, dados);
                response.put("Mensagem", "Dados atualizados!");
                response.put("Dados", usuarioAtualizar);

                return ResponseEntity.ok(response);

            }catch (RuntimeException e){
               response.put("Mensagem", e.getMessage());

                return ResponseEntity.status(400).body(response);
            }
     }

     @PutMapping("/senha")
     public ResponseEntity<?> atualizarSenha(@RequestHeader("Authorization") String hearder, @RequestBody AtualizarSenhaDTO dados){

        String token = authUtil.validarHearder(hearder);

              try {
                  service.atualizarSenha(token, dados);
                  response.put("Mensagem", "Senha Atualizada com sucesso");
                  return ResponseEntity.ok(response);

              }catch (RuntimeException e){
                  response.put("Mensagem", e.getMessage());
                  return ResponseEntity.status(400).body(response);
              }
     }


     @DeleteMapping("/me")
     public ResponseEntity<?> delete(@RequestHeader("Authorization") String hearder){
        String token = authUtil.validarHearder(hearder);

        if(token == null){
            response.put("Mensagem", "O usuário não existe!");
            return ResponseEntity.status(401).body(response);
        }
        service.delete(token);
        response.put("Mensagem:", "Conta Excluida!");

             return ResponseEntity.ok(response);
     }

}
