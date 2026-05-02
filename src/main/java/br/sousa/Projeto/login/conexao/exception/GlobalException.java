package br.sousa.Projeto.login.conexao.exception;

import br.sousa.Projeto.login.conexao.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO> Exception(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(e.getMessage(), 400));
    }
}
