package com.mobeelizer.java.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobeelizerErrorsBuilder {

    private final Map<String, List<MobeelizerError>> errors = new HashMap<String, List<MobeelizerError>>();

    public static class MobeelizerErrorImpl implements MobeelizerError {

        private final MobeelizerErrorCode code;

        private final String message;

        private final List<Object> args;

        private MobeelizerErrorImpl(final MobeelizerErrorCode code, final String message, final Object... args) {
            this.code = code;
            this.message = message;
            this.args = Arrays.asList(args);
        }

        @Override
        public MobeelizerErrorCode getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public List<Object> getArgs() {
            return args;
        }

    }

    public boolean hasNoErrors() {
        return errors.isEmpty();
    }

    public void addFieldCanNotBeEmpty(final String field) {
        addError(field, MobeelizerErrorCode.EMPTY, MobeelizerErrorCode.EMPTY.getMessage());
    }

    public void addFieldIsTooLong(final String field, final int maxLength) {
        addError(field, MobeelizerErrorCode.TOO_LONG, String.format(MobeelizerErrorCode.TOO_LONG.getMessage(), maxLength),
                maxLength);
    }

    public void addFieldMustBeLessThan(final String field, final Long maxValue) {
        addError(field, MobeelizerErrorCode.LESS_THAN,
                String.format(MobeelizerErrorCode.LESS_THAN.getMessage(), Long.toString(maxValue)), maxValue);
    }

    public void addFieldMustBeGreaterThan(final String field, final Long minValue) {
        addError(field, MobeelizerErrorCode.GREATER_THAN,
                String.format(MobeelizerErrorCode.GREATER_THAN.getMessage(), Long.toString(minValue)), minValue);
    }

    public void addFieldMustBeLessThan(final String field, final BigDecimal maxValue) {
        addError(field, MobeelizerErrorCode.LESS_THAN,
                String.format(MobeelizerErrorCode.LESS_THAN.getMessage(), maxValue.toPlainString()), maxValue);
    }

    public void addFieldMustBeGreaterThanOrEqual(final String field, final BigDecimal minValue) {
        addError(field, MobeelizerErrorCode.GREATER_THAN_OR_EQUAL_TO,
                String.format(MobeelizerErrorCode.GREATER_THAN_OR_EQUAL_TO.getMessage(), minValue.toPlainString()), minValue);
    }

    public void addFieldMissingReferenceError(final String field, final String uuid) {
        addError(field, MobeelizerErrorCode.NOT_FOUND, String.format(MobeelizerErrorCode.NOT_FOUND.getMessage(), uuid), uuid);
    }

    public void addFieldMustBeLessThanOrEqualTo(final String field, final BigDecimal maxValue) {
        addError(field, MobeelizerErrorCode.LESS_THAN_OR_EQUAL_TO,
                String.format(MobeelizerErrorCode.LESS_THAN_OR_EQUAL_TO.getMessage(), maxValue.toPlainString()), maxValue);
    }

    public void addFieldMustBeGreaterThan(final String field, final BigDecimal minValue) {
        addError(field, MobeelizerErrorCode.GREATER_THAN,
                String.format(MobeelizerErrorCode.GREATER_THAN.getMessage(), minValue.toPlainString()), minValue);
    }

    public void addNoCredentialsToPerformOperationOnModel(final String operation) {
        addError(null, MobeelizerErrorCode.NO_CREDENTIALS_TO_PERFORM_OPERATION_ON_MODEL,
                String.format(MobeelizerErrorCode.NO_CREDENTIALS_TO_PERFORM_OPERATION_ON_MODEL.getMessage(), operation),
                operation);
    }

    public void addNoCredentialsToPerformOperationOnField(final String field, final String operation) {
        addError(null, MobeelizerErrorCode.NO_CREDENTIALS_TO_PERFORM_OPERATION_ON_FIELD,
                String.format(MobeelizerErrorCode.NO_CREDENTIALS_TO_PERFORM_OPERATION_ON_FIELD.getMessage(), operation, field),
                field);
    }

    private void addError(final String field, final MobeelizerErrorCode code, final String message, final Object... args) {
        if (!errors.containsKey(field)) {
            errors.put(field, new ArrayList<MobeelizerError>());
        }

        errors.get(field).add(new MobeelizerErrorImpl(code, message, args));
    }

    public MobeelizerErrors createWhenErrors() {
        if (!hasNoErrors()) {
            return new MobeelizerErrorsImpl(errors);
        }
        return null;
    }

}
