package br.com.biblioteca.service.exception;

public class CpfInvalidoFoundException extends RuntimeException {
    public CpfInvalidoFoundException(String msg) {
        super(msg);
    }

    public CpfInvalidoFoundException() {
    }
}
