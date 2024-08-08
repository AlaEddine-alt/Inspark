package com.inspark.sabeel.auth.infrastructure.exception;

import java.io.Serial;

public class NotFoundException extends ApplicationException {

    @Serial
    private static final long serialVersionUID = 5477619054099558741L;

    public enum NotFoundExceptionType implements ExceptionType {

        GENERIC("error.server.notfound.title", "error.server.notfound.msg", "Resource not found 400"),
        USER_NOT_FOUND("error.server.notfound.title.user",
                "error.server.notfound.msg.user", "User not found : {0}"),
        CODE_NOT_FOUND("error.server.notfound.title.code",
                "error.server.notfound.msg.code", "Code not found : {0}"),
        CODE_USED("error.server.notfound.title.code.used",
                "error.server.notfound.msg.code.used", "Code is already used : {0}"),
        CODE_EXPIRED("error.server.notfound.title.code.expired",
                "error.server.notfound.msg.code.expired", "Code is expired : {0}");


        private final String messageKey;
        private final String titleKey;
        private final String messageCause;

        NotFoundExceptionType(String titleKey, String messageKey, String messageCause) {
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

    public NotFoundException(NotFoundExceptionType type) {
        super(type);
    }

    public NotFoundException(NotFoundExceptionType type, Throwable cause) {
        super(type, cause);
    }

    public NotFoundException(NotFoundExceptionType type, String message, Throwable cause) {
        super(type, message, cause);
    }

    public NotFoundException(NotFoundExceptionType type, String message, Throwable cause, Object... keyParams) {
        super(type, message, cause, keyParams);
    }

    public NotFoundException(NotFoundExceptionType type, Object[] valueParams, Object... keyParams) {
        super(type, valueParams, keyParams);
    }

    public NotFoundException(NotFoundExceptionType type, Object... valueParams) {
        super(type, valueParams);
    }
}
