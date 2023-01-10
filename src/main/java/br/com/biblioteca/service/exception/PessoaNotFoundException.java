package br.com.biblioteca.service.exception;

public class PessoaNotFoundException extends RuntimeException {
    public PessoaNotFoundException(String msg) {
        super(msg);
    }

    public PessoaNotFoundException() {
    }
}
