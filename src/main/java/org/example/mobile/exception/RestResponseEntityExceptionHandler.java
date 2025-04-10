package org.example.mobile.exception;

import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CommonException.class})
    protected ResponseEntity<Object> handleCommonException(CommonException ex, WebRequest request) {
        log.error("CommonException: ", ex);
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(new Result(ex.getMessage()));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleValidException(MethodArgumentNotValidException ex, WebRequest request) {
        log.error("MethodArgumentNotValidException: ", ex);
        String errorMsg = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        return ResponseEntity
                .badRequest()
                .body(new Result(errorMsg));
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    protected ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        log.error("NoSuchElementException: ", ex);
        return ResponseEntity
                .status(400)
                .body(new Result("Không tìm thấy thông tin tương ứng."));
    }

    @ExceptionHandler(value = {SignatureException.class})
    protected ResponseEntity<Object> handleSignatureException(SignatureException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Result("Token không hợp lệ"));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        log.error("GlobalException: ", ex);
        return ResponseEntity.internalServerError().body(new Result(ex.getMessage()));
    }

}
