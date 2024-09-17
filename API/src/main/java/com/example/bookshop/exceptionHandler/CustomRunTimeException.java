package com.example.bookshop.exceptionHandler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CustomRunTimeException extends RuntimeException{
    private ErrorCode errorCode;
    public CustomRunTimeException(ErrorCode errorCode) {
            super(errorCode.getMessage());
            this.errorCode = errorCode;
    }

}
