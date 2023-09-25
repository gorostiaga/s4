package com.truextend.s4.exceptions;

import com.truextend.s4.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
/*    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(NotFoundException e){

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(e.getMessage());
        response.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);

    }*/

    @ExceptionHandler
    public ResponseEntity<ApiResponse> handlerException(NotFoundException e){

        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(e.getMessage());
        response.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

/*    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(Exception e){

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(e.getMessage());
        response.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
    }*/

    @ExceptionHandler
    public ResponseEntity<ApiResponse> handlerException(Exception e){

        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(e.getMessage());
        response.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
