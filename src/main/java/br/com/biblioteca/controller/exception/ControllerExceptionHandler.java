package br.com.biblioteca.controller.exception;

import br.com.biblioteca.service.exception.CpfInvalidoFoundException;
import br.com.biblioteca.service.exception.PessoaNotFoundException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(PessoaNotFoundException.class)
    public ResponseEntity<ErroPadrao> pessoaNotFoundException(PessoaNotFoundException ex,
                                                              HttpServletRequest request) {

        ErroPadrao error = new ErroPadrao(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.toString(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CpfInvalidoFoundException.class)
    public ResponseEntity<ErroPadrao> cpfInvalidoException(CpfInvalidoFoundException ex,
                                                           HttpServletRequest request) {

        ErroPadrao error = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErroPadrao> cepInvalidoException(HttpClientErrorException ex,
                                                           HttpServletRequest request) {

        ErroPadrao error = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(), "CEP infomado é inválido", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErroPadrao> pessoaExistenteFoundException(PSQLException ex,
                                                                    HttpServletRequest request) {

        ErroPadrao error = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(), "CPF JÀ EXISTE", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
