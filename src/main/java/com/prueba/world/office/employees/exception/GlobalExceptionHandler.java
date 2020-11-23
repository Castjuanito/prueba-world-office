package com.prueba.world.office.employees.exception;

import com.prueba.world.office.employees.dto.response.Response;
import com.prueba.world.office.employees.dto.response.ResponseError;
import com.prueba.world.office.employees.dto.response.ResponseStatus;
import com.prueba.world.office.employees.exception.custom_exceptions.EntityNotFoundException;
import com.prueba.world.office.employees.exception.custom_exceptions.FileException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Response> entityNotFound(EntityNotFoundException ex) {
        ResponseError error = ResponseError.createErrorFromException(ex, Optional.empty());
        Response response = Response
                .builder()
                .status(ResponseStatus.NOT_FOUND)
                .error(error)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> generalResponse(Exception ex, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String errorTemplate = EntityType.OTHER + "." + ExceptionType.GENERAL_EXCEPTION.getValue();
        ResponseError error = ResponseError
                .createErrorFromException(AppException.throwException(errorTemplate.toLowerCase()),
                        Optional.empty());
        Response response = Response.builder()
                .status(ResponseStatus.EXCEPTION)
                .error(error)
                .build();
        return new ResponseEntity<>(
                response,
                (ex instanceof NullPointerException) ?
                        HttpStatus.BAD_REQUEST :
                        HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<Response> fileException(FileException ex) {
        ResponseError error = ResponseError.createErrorFromException(ex, Optional.empty());
        Response response = Response
                .builder()
                .status(ResponseStatus.EXCEPTION)
                .error(error)
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
