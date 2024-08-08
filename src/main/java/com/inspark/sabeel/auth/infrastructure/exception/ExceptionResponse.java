package com.inspark.sabeel.auth.infrastructure.exception;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class ExceptionResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 3620491867032462290L;

    /**
     * custom message
     */
    private final String message;

    /**
     * custom title
     */
    private final String title;

    /**
     * message from the exception
     */
    private String exceptionMessage;

    /**
     * type of the exception
     */
    private Class<?> exceptionType;

    public ExceptionResponse(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public ExceptionResponse(String title, String message, Exception exceptionSource) {
        this(title, message);
        if (exceptionSource != null) {
            this.exceptionMessage = exceptionSource.getMessage();
            this.exceptionType = exceptionSource.getClass();
        }
    }

}
