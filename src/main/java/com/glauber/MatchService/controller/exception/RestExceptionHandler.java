package com.glauber.MatchService.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.ServiceUnavailableException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(EmailSendException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse emailSendException(EmailSendException e) {
        var exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Erro interno no servidor ao enviar o e-mail",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
        );
        return exceptionResponseBuilder.build();
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ExceptionResponse emailSendException(ServiceUnavailableException e) {
        var exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Serviço de e-mail temporariamente indisponível",
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                e.getMessage()
        );
        return exceptionResponseBuilder.build();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse nullPointerException(NullPointerException e) {
        var exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Erro interno do servidor, objeto nulo encontrado",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
        );
        return exceptionResponseBuilder.build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse noSUchElementException(NoSuchElementException e) {
        var exceptionResponseBuilder = new ExceptionResponseBuilder(
                "O recurso não foi encontrado. Verifique os parâmetros da sua solicitação",
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        return exceptionResponseBuilder.build();
    }
}
