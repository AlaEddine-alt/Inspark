package com.inspark.sabeel.exception;

import java.io.Serial;


public class BadRequestException extends ApplicationException {
    @Serial
    private static final long serialVersionUID = 1152907649742554198L;

    public enum BadRequestExceptionType implements ExceptionType {
        PASSWORD_MISMATCH("error.server.conflict.password-mismatch.title",
                "error.server.conflict.password-mismatch.msg",
                "Password mismatch"),
        WRONG_PASSWORD("error.server.conflict.wrong-password.title",
                "error.server.conflict.wrong-password.msg",
                "Wrong password"),
        PERMISSION_DENIED("error.server.bad-request.permission-denied.title",
                "error.server.bad-request.permission-denied.msg",
                "Permission denied");

        private final String messageKey;
        private final String titleKey;
        private final String messageCause;

        BadRequestExceptionType(String titleKey, String messageKey, String messageCause) {
            this.messageKey = messageKey;
            this.titleKey = titleKey;
            this.messageCause = messageCause;
        }

        @Override
        public String getTitleKey() {
            return titleKey;
        }

        @Override
        public String getMessageKey() {
            return messageKey;
        }

        @Override
        public String getMessageCause() {
            return messageCause;
        }
    }

    public BadRequestException(BadRequestExceptionType type) {
        super(type);
    }

    public BadRequestException(BadRequestExceptionType type, Throwable cause) {
        super(type, cause);
    }

    public BadRequestException(BadRequestExceptionType type, String message, Throwable cause) {
        super(type, message, cause);
    }

    public BadRequestException(BadRequestExceptionType type, String message, Throwable cause, Object... keyParams) {
        super(type, message, cause, keyParams);
    }

    public BadRequestException(BadRequestExceptionType type, Object[] valueParams, Object... keyParams) {
        super(type, valueParams, keyParams);
    }

    public BadRequestException(BadRequestExceptionType type, Throwable cause, Object[] valueParams, Object... keyParams) {
        super(type, cause, valueParams, keyParams);
    }

    public BadRequestException(BadRequestExceptionType type, Object... valueParams) {
        super(type, valueParams);
    }
}
