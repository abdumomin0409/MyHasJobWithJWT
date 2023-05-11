package com.company.job.myhasjobwithjwt.config.ex_handler;

import com.company.job.myhasjobwithjwt.payload.AppErrorDTO;
import com.company.job.myhasjobwithjwt.payload.ResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO<Void>> handler_400(HttpServletRequest request, RuntimeException e) {
        AppErrorDTO appErrorDTO = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 400);
        return ResponseEntity.badRequest().body(new ResponseDTO<>(appErrorDTO));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<Void>> handler_500(HttpServletRequest request, Exception e) {
        AppErrorDTO appErrorDTO = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 500);
        return ResponseEntity.status(500).body(new ResponseDTO<>(appErrorDTO));
    }

    @ExceptionHandler(Error.class)
    public ResponseEntity<ResponseDTO<Void>> error_handler_400(HttpServletRequest request, Error e) {
        AppErrorDTO appErrorDTO = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 400);
        return ResponseEntity.status(400).body(new ResponseDTO<>(appErrorDTO));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ResponseDTO<Void>> handler_403(HttpServletRequest request, DisabledException e) {
        AppErrorDTO appErrorDTO = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 403);
        return ResponseEntity.status(403).body(new ResponseDTO<>(appErrorDTO));
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<ResponseDTO<Void>> handlerCredentialsExpiredExceptions(HttpServletRequest request, CredentialsExpiredException e) {
        AppErrorDTO appErrorDTO = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 403);
        return ResponseEntity.status(403).body(new ResponseDTO<>(appErrorDTO));
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ResponseDTO<Void>> handlerInsufficientAuthenticationException(HttpServletRequest request, InsufficientAuthenticationException e) {
        AppErrorDTO appErrorDTO = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 400);
        return ResponseEntity.status(400).body(new ResponseDTO<>(appErrorDTO));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseDTO<Void>> handlerExpiredJwtException(HttpServletRequest request, ExpiredJwtException e) {
        AppErrorDTO appErrorDTO = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 400);
        return ResponseEntity.status(400).body(new ResponseDTO<>(appErrorDTO));
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ResponseDTO<Void>> handlerExpiredJwtException(HttpServletRequest request, NumberFormatException e) {
        AppErrorDTO appErrorDTO = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 400);
        return ResponseEntity.status(400).body(new ResponseDTO<>(appErrorDTO));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Void>> handlerMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        String errorMessage = "Input is not valid";
        Map<String, List<String>> errorBody = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errorBody.compute(field, (s, values) -> {
                if (!Objects.isNull(values))
                    values.add(message);
                else
                    values = new ArrayList<>(Collections.singleton(message));
                return values;
            });
        }
        String errorPath = request.getRequestURI();
        AppErrorDTO error = new AppErrorDTO(errorPath, errorMessage, errorBody, 400);
        return ResponseEntity.status(400).body(new ResponseDTO<>(error));
    }


}
