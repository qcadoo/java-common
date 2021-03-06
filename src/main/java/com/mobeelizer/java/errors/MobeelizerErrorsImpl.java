package com.mobeelizer.java.errors;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.mobeelizer.java.api.MobeelizerError;
import com.mobeelizer.java.api.MobeelizerErrors;

/**
 * Representation of the database exception.
 * 
 * @since 1.0
 */
public class MobeelizerErrorsImpl implements MobeelizerErrors {

    private final Map<String, List<MobeelizerError>> errors;

    public MobeelizerErrorsImpl(final Map<String, List<MobeelizerError>> errors) {
        this.errors = errors;
    }

    @Override
    public List<MobeelizerError> getGlobalErrors() {
        return getFieldErrors(null);
    }

    @Override
    public boolean isFieldValid(final String field) {
        return !errors.containsKey(field);
    }

    @Override
    public List<MobeelizerError> getFieldErrors(final String field) {
        if (errors.containsKey(field)) {
            return errors.get(field);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Set<String> getInvalidFields() {
        Set<String> invaliedFields = errors.keySet();
        invaliedFields.remove(null);
        return invaliedFields;
    }

    @Override
    public String toString() {
        return errorsToString(errors);
    }

    private static String errorsToString(final Map<String, List<MobeelizerError>> errors) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        errorMessageBuilder.append("ERROR:\n");
        if (errors.get(null) != null) {
            for (MobeelizerError error : errors.get(null)) {
                errorMessageBuilder.append(error.getCode() + ": " + error.getMessage() + "\n");
            }
        }
        for (Entry<String, List<MobeelizerError>> fieldError : errors.entrySet()) {
            if (fieldError.getKey() == null) {
                continue;
            }
            for (MobeelizerError error : fieldError.getValue()) {
                errorMessageBuilder.append("field '" + fieldError.getKey() + "' - " + error.getCode() + ": " + error.getMessage()
                        + "\n");
            }
        }
        return errorMessageBuilder.toString();
    }
}
