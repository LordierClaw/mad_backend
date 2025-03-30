package org.example.mobile.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class CommonException extends RuntimeException {
    private HttpStatus httpStatus;

    public CommonException(HttpStatus httpStatus, String errorMessage) {
        super(errorMessage);
        this.httpStatus = httpStatus;
    }
}
