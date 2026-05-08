package br.sousa.Projeto.login.conexao.controller;

import br.sousa.Projeto.login.conexao.dto.*;

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
    public ResponseEntity<?> cadastrar(@RequestBody UsuarioRequestDto usuario) {

        service.cadastrarUsuario(usuario);
        return ResponseEntity
                .status(201)
                .body(new ResponseDTO("Usuário cadastrado", 201));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioRequestDto request) {
        String token = service.login(request.getEmail(), request.getSenha());
        LoginResponseDto user = service.buscarPorEmail(request.getEmail());
        Map<String, Object> reponse = new LinkedHashMap<>();

        reponse.put("mensagem", "Usuário logado");
        reponse.put("status", 200);
        reponse.put("usuario", user);
        reponse.put("token", token);

        return ResponseEntity.ok(reponse);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> heard(@RequestHeader("Authorization") String header) {

        service.admin(header);
        Map<String, Object> response = new LinkedHashMap<>();

                 response.put("mensagem", "| PAINEL ADMIN |");
                 response.put("users", service.lista());
                 return ResponseEntity.ok(response);

}

       @GetMapping("/me")
     public ResponseEntity<?> usuario(@RequestHeader("Authorization") String hearder){
        String user = authUtil.getEmail(hearder);

              UsuarioResponseDTO usuario = service.buscarPorToken(user);
           Map<String, Object> reponse = new LinkedHashMap<>();

           reponse.put("mensagem", "Sua Conta");
           reponse.put("dados",usuario);

              return ResponseEntity.ok(reponse);
     }
     @PutMapping("/me")
     public ResponseEntity<?> atualizarDados(@RequestHeader("Authorization") String hearder, @RequestBody UsuarioResumoDTO dados){
        String token = authUtil.getEmail(hearder);

        UsuarioResumoDTO usuarioAtualizar = service.atualizarDados(token, dados);

        Map<String, Object> reponse = new LinkedHashMap<>();
                reponse.put("mensagem", "Dados atualizados:");
                reponse.put("dados", usuarioAtualizar);

                return ResponseEntity.ok(reponse);
     }

     @PutMapping("/senha")
     public ResponseEntity<?> atualizarSenha(@RequestHeader("Authorization") String hearder, @RequestBody AtualizarSenhaDTO dados){

        String token = authUtil.getEmail(hearder);

                  service.atualizarSenha(token, dados);

                  return ResponseEntity
                          .status(200)
                          .body(new ResponseDTO("Senha atualizada com sucesso", 200));

     }
     @PutMapping("/ADD")
     public ResponseEntity<?> atualizarCargo(@RequestHeader("Authorization") String header, @RequestBody UserRequestDTO dados){

        service.setCargo(header ,dados.getEmail(), dados.getRole());

        return ResponseEntity
                .ok(new ResponseDTO("Dados atualizados", 200));
     }

     @DeleteMapping("/me")
     public ResponseEntity<?> delete(@RequestHeader("Authorization") String hearder){

        service.delete(hearder);
        return ResponseEntity
                .ok(new ResponseDTO("Conta excluida", 200));
     }

}
