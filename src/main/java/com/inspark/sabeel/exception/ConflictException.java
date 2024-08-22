package com.inspark.sabeel.exception;


import java.io.Serial;

public class ConflictException extends ApplicationException {
    @Serial
    private static final long serialVersionUID = 1152907649742554198L;

    public enum ConflictExceptionType implements ExceptionType {

        CONFLICT_LOCK_VERSION("error.server.conflict.lock-version.title",
                "error.server.conflict.lock-version.msg",
                "Cannot save object {0}. An optimistic locking conflict occurs because a more recent version of the entity exists in the database : {1}"),
        CONFLICT_MISSING_VERSION("error.server.conflict.lock-version.title",
                "error.server.conflict.lock-version.msg",
                "Cannot save object {0}. An optimistic locking conflict occurs because a more recent version of the entity exists in the database : {1}"),
        EMAIL_ALREADY_VERIFIED("error.server.conflict.email-already-verified.title",
                "error.server.conflict.email-already-verified.msg",
                "Email already verified");


        private final String messageKey;
        private final String titleKey;
        private final String messageCause;

        ConflictExceptionType(String titleKey, String messageKey, String messageCause) {
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

    public ConflictException(ConflictExceptionType type) {
        super(type);
    }

    public ConflictException(ConflictExceptionType type, Throwable cause) {
        super(type, cause);
    }

    public ConflictException(ConflictExceptionType type, String message, Throwable cause) {
        super(type, message, cause);
    }

    public ConflictException(ConflictExceptionType type, String message, Throwable cause, Object... keyParams) {
        super(type, message, cause, keyParams);
    }

    public ConflictException(ConflictExceptionType type, Object[] valueParams, Object... keyParams) {
        super(type, valueParams, keyParams);
    }

    public ConflictException(ConflictExceptionType type, Throwable cause, Object[] valueParams, Object... keyParams) {
        super(type, cause, valueParams, keyParams);
    }

    public ConflictException(ConflictExceptionType type, Object... valueParams) {
        super(type, valueParams);
    }
}
