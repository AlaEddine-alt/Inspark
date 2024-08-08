package com.inspark.sabeel.auth.infrastructure.exception;

public interface ExceptionType {
    String getTitleKey();

    String getMessageKey();

    String getMessageCause();
}
