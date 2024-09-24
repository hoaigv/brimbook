package com.example.bookshop.exceptionHandlers;

import com.example.bookshop.utils.ApiResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.View;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        ApiResponse<?> apiResponse = new ApiResponse<>();

        apiResponse.setMessage(ErrorCode.URL_NOT_EXIST.getMessage());
        apiResponse.setCode(ErrorCode.URL_NOT_EXIST.getCode());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException() {
        ApiResponse<?> apiResponse = new ApiResponse<>();

        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleConstraintViolationException(ConstraintViolationException e) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(e.getConstraintViolations().iterator().next().getMessage());
        apiResponse.setCode(1999);
        return ResponseEntity.badRequest().body(apiResponse);
    }


    @ExceptionHandler(value = CustomRunTimeException.class)
    public ResponseEntity<ApiResponse<?>> handleCusTomRuntimeException(CustomRunTimeException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse<?> apiResponse = new ApiResponse<>();

        apiResponse.setMessage(e.getMessage());
        apiResponse.setCode(errorCode.getCode());
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleIllegalArgumentException(IllegalArgumentException e) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(e.getMessage());
        apiResponse.setCode(400);
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = BadJwtException.class)
    public ResponseEntity<ApiResponse<?>> handleBadJwtException(BadJwtException e) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(ErrorCode.UNAUTHENTICATED.getMessage());
        apiResponse.setCode(ErrorCode.UNAUTHENTICATED.getCode());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiResponse<?>> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        ApiResponse<?> response = new ApiResponse<>();
        response.setMessage("Missing request part. Please check your form!");
        response.setCode(400);
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Invalid input data");

        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(errorMessage);
        apiResponse.setCode(400);

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDuplicateEntryException(DataIntegrityViolationException ex) {
        String errorMessage = ex.getCause() != null ?  ex.getMessage() : "";
        if(ex.getMessage().contains("Duplicate")){
            errorMessage = ex.getMessage();
        }
        ApiResponse<?> apiResponse = new ApiResponse<>();
        if (errorMessage.contains("Duplicate")) {
            Pattern pattern = Pattern.compile("'(.*?)'");
            Matcher matcher = pattern.matcher(errorMessage);
            String duplicateValue = "";
            if (matcher.find()) {
                duplicateValue = matcher.group(1);
            }
            String userMessage = "The value '" + duplicateValue + "' is already in use. Please choose another.";
           apiResponse.setMessage(userMessage);
           apiResponse.setCode(409);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        } else {
            apiResponse.setMessage(ex.getMessage() +"\n"+"Hoai");
            apiResponse.setCode(400);
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }



}
