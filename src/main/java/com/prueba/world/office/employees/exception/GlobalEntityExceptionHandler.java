package com.prueba.world.office.employees.exception;

import com.prueba.world.office.employees.dto.response.Response;
import com.prueba.world.office.employees.dto.response.ResponseError;
import com.prueba.world.office.employees.dto.response.ResponseStatus;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response response = handleGeneralExeption(ex, headers, status, request);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Response handleGeneralExeption(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        String errorTemplate = EntityType.OTHER + "." + ExceptionType.VALIDATION_EXCEPTION.getValue();
        ResponseError error = ResponseError
                .createErrorFromException(AppException.throwException(errorTemplate.toLowerCase()),
                        Optional.of(errors));
        return Response.builder()
                .status(ResponseStatus.BAD_REQUEST)
                .error(error)
                .build();
    }

}

