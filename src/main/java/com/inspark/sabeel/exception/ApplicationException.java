package com.inspark.sabeel.exception;

import java.io.Serial;
import java.text.MessageFormat;

public abstract class ApplicationException extends RuntimeException {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 7144041535587769114L;

    private final String messageKey;

    private final String titleKey;

    /**
     * Create an application exception with exception type fields
     *
     * @param type exception type
     */
    protected ApplicationException(ExceptionType type) {
        this(type, type.getMessageCause());

    }

    /**
     * Create an application exception and format the messageCause, messageKey and
     * titleKey fields with the value and key parameters
     *
     * @param type        exception type
     * @param valueParams params value for messageCause field
     * @param keyParams   optionnal key params to format the message and title key
     */
    protected ApplicationException(ExceptionType type, Object[] valueParams, Object... keyParams) {
        super(MessageFormat.format(type.getMessageCause(), valueParams));
        this.messageKey = MessageFormat.format(type.getMessageKey(), keyParams);
        this.titleKey = MessageFormat.format(type.getTitleKey(), keyParams);
    }

    /**
     * Create an application exception and format the messageCause, messageKey and
     * titleKey fields with the value and key parameters
     *
     * @param type        exception type
     * @param cause       cause exception
     * @param valueParams params value for messageCause field
     * @param keyParams   optionnal key params to format the message and title key
     */
    protected ApplicationException(ExceptionType type, Throwable cause, Object[] valueParams, Object... keyParams) {
        super(MessageFormat.format(type.getMessageCause(), valueParams), cause);
        this.messageKey = MessageFormat.format(type.getMessageKey(), keyParams);
        this.titleKey = MessageFormat.format(type.getTitleKey(), keyParams);
    }

    /**
     * Create an application exception with a specific cause exception.
     *
     * @param type  exception type
     * @param cause cause exception
     */
    protected ApplicationException(ExceptionType type, Throwable cause) {
        super(type.getMessageCause() != null ? type.getMessageCause() : cause.getMessage(), cause);
        this.messageKey = type.getMessageKey();
        this.titleKey = type.getTitleKey();
    }

    /**
     * Create an application exception and format the messageKey and titleKey
     * fields with the key parameters
     *
     * @param type      exception type
     * @param message   the message of the exception
     * @param cause     the cause of the exception
     * @param keyParams key parameters to format the message and title key
     */
    protected ApplicationException(ExceptionType type, String message, Throwable cause, Object... keyParams) {
        super(message, cause);
        this.messageKey = MessageFormat.format(type.getMessageKey(), keyParams);
        this.titleKey = MessageFormat.format(type.getTitleKey(), keyParams);
    }

    /**
     * Create an application exception and format the messageCause with the value
     * parameters
     *
     * @param type        exception type
     * @param cause       the cause of the exception
     * @param valueParams params value for messageCause field
     */
    protected ApplicationException(ExceptionType type, Throwable cause, Object... valueParams) {
        super(MessageFormat.format(type.getMessageCause(), valueParams), cause);
        this.messageKey = type.getMessageKey();
        this.titleKey = type.getTitleKey();
    }

    /**
     * Create an application exception with a specific message exception
     *
     * @param type    exception type
     * @param message message exception
     */
    protected ApplicationException(ExceptionType type, String message) {
        super(message);
        this.messageKey = type.getMessageKey();
        this.titleKey = type.getTitleKey();
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getTitleKey() {
        return titleKey;
    }
}
