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

    Map<String,Object> response = new LinkedHashMap<>();

    @PostMapping("/cadastro")
     public ResponseEntity<?> cadastrar(@RequestBody UsuarioRequestDto usuario){

            service.cadastrarUsuario(usuario);
            return ResponseEntity
                    .status(201)
                    .body(new ResponseDTO("Usuário cadastrado",201));
     }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioRequestDto request){
           String token = service.login(request.getEmail(), request.getSenha());
        LoginResponseDto user = service.buscarPorEmail(request.getEmail());

               response.put("mensagem", "Usuário logado!");
               response.put("status", 200);
               response.put("usuario", user);
               response.put("token", token);

            return ResponseEntity.status(200).body(response);

    }
     @GetMapping("/admin")
     public ResponseEntity<?> heard(@RequestHeader("Authorization") String hearder){

        String email = authUtil.validarHearder(hearder);

        if(email == null){
            return ResponseEntity
                    .status(401)
                    .body(new ResponseDTO("Usuário não autorizado", 401));
        }
        response.put("usuarios", service.lista());
        return ResponseEntity.ok(response);
     }

       @GetMapping("/me")
     public ResponseEntity<?> usuario(@RequestHeader("Authorization") String hearder){
        String user = authUtil.validarHearder(hearder);

              UsuarioResponseDTO usuario = service.buscarPorToken(user);

              response.put("mensagem", "Sua Conta");
              response.put("dados", usuario);
              return ResponseEntity.ok(response);

     }
     @PutMapping("/me")
     public ResponseEntity<?> atualizarDados(@RequestHeader("Authorization") String hearder, @RequestBody UsuarioResumoDTO dados){
        String token = authUtil.validarHearder(hearder);

                UsuarioResumoDTO usuarioAtualizar = service.atualizarDados(token, dados);
                response.put("mensagem", "Dados atualizados!");
                response.put("dados", usuarioAtualizar);

                return ResponseEntity.status(200).body(response);

     }

     @PutMapping("/senha")
     public ResponseEntity<?> atualizarSenha(@RequestHeader("Authorization") String hearder, @RequestBody AtualizarSenhaDTO dados){

        String token = authUtil.validarHearder(hearder);

                  service.atualizarSenha(token, dados);

                  return ResponseEntity
                          .status(200)
                          .body(new ResponseDTO("Senha atualizada com sucesso", 200));

     }


     @DeleteMapping("/me")
     public ResponseEntity<?> delete(@RequestHeader("Authorization") String hearder){
        String token = authUtil.validarHearder(hearder);

        if(token == null){
            return ResponseEntity.status(404).body(new ResponseDTO("O usuário não existe", 404));
        }
        service.delete(token);
             return ResponseEntity.ok(new ResponseDTO("Conta excluida", 200));
     }

}
