// 
// MobeelizerTextFieldTypeHelper.java
// 
// Copyright (C) 2012 Mobeelizer Ltd. All Rights Reserved.
//
// Mobeelizer SDK is free software; you can redistribute it and/or modify it 
// under the terms of the GNU Affero General Public License as published by 
// the Free Software Foundation; either version 3 of the License, or (at your
// option) any later version.
//
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
// for more details.
//
// You should have received a copy of the GNU Affero General Public License 
// along with this program; if not, write to the Free Software Foundation, Inc., 
// 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
// 

package com.mobeelizer.java.definition.type.helpers;

import static com.mobeelizer.java.model.MobeelizerReflectionUtil.setValue;

import java.util.Map;

import com.mobeelizer.java.api.MobeelizerDatabaseExceptionBuilder;
import com.mobeelizer.java.definition.type.options.MobeelizerFieldOptions;
import com.mobeelizer.java.definition.type.options.MobeelizerTextFieldOptions;
import com.mobeelizer.java.definition.type.options.type.MobeelizerIntegerFieldOptionTypeHelper;
import com.mobeelizer.java.definition.type.options.type.MobeelizerModelFieldOption;
import com.mobeelizer.java.model.MobeelizerFieldAccessor;

public class MobeelizerTextFieldTypeHelper extends MobeelizerFieldTypeHelper {

    public static final String MAX_LENGTH = "maxLength";

    public MobeelizerTextFieldTypeHelper() {
        super(String.class);
        addOption(new MobeelizerModelFieldOption(MAX_LENGTH, new MobeelizerIntegerFieldOptionTypeHelper(), false));
    }

    @Override
    public Class<?> getDefaultAccessibleType() {
        return String.class;
    }

    @Override
    public String convertFromEntityValueToJsonValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerDatabaseExceptionBuilder errors) {
        String stringValue = (String) value;

        if (!validateValue(field, stringValue, options, errors)) {
            return null;
        }

        return stringValue;
    }

    @Override
    public Object convertFromJsonValueToEntityValue(final MobeelizerFieldAccessor field, final String value) {
        return value;
    }

    @Override
    public Object convertFromDatabaseValueToEntityValue(final MobeelizerFieldAccessor field, final Object value) {
        return value;
    }

    @Override
    public Object convertFromEntityValueToDatabaseValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerDatabaseExceptionBuilder errors) {
        return convertFromEntityValueToJsonValue(field, value, options, errors);
    }

    @Override
    public boolean validateValue(final MobeelizerFieldAccessor field, final Object value, final Map<String, String> options,
            final MobeelizerDatabaseExceptionBuilder errors) {
        int maxLength = getMaxLength(options);

        if (((String) value).length() > maxLength) {
            errors.addFieldIsTooLong(field.getName(), maxLength);
            return false;
        }

        return true;
    }

    @Override
    public Object convertDefaultValue(final MobeelizerFieldAccessor field, final String defaultValue,
            final Map<String, String> options) {
        return defaultValue;
    }

    @Override
    protected void setNotNullFromEntityToJsonEntity(final Map<String, String> values, final Object value,
            final MobeelizerFieldAccessor field, final Map<String, String> options,
            final MobeelizerDatabaseExceptionBuilder errors) {
        String stringValue = convertFromEntityValueToJsonValue(field, value, options, errors);

        if (!errors.hasNoErrors()) {
            return;
        }

        values.put(field.getName(), stringValue);
    }

    @Override
    protected void setNullValueFromEntityToJsonEntity(final Map<String, String> values, final MobeelizerFieldAccessor field,
            final Map<String, String> options, final MobeelizerDatabaseExceptionBuilder errors) {
        // empty
    }

    @Override
    protected void setNullValueFromJsonEntityToEntity(final MobeelizerFieldAccessor field, final Map<String, String> options,
            final Object entity) {
        // empty
    }

    @Override
    protected void setNotNullValueFromJsonEntityToEntity(final MobeelizerFieldAccessor field, final String value,
            final Map<String, String> options, final Object entity) {
        setValue(field, entity, convertFromJsonValueToEntityValue(field, value));
    }

    private int getMaxLength(final Map<String, String> options) {
        return options.containsKey("maxLength") ? Integer.valueOf(options.get("maxLength")) : 4096;
    }

    @Override
    public String validateAndNormalizeValue(final String value, final MobeelizerFieldOptions options) {
        MobeelizerTextFieldOptions textOptions = (MobeelizerTextFieldOptions) options;

        if (value.length() > textOptions.getMaxLength()) {
            throw new IllegalStateException("Text is too long, max length is " + textOptions.getMaxLength());
        }

        return value;
    }

    @Override
    protected Class<? extends MobeelizerFieldOptions> getOptionObjectClass() {
        return MobeelizerTextFieldOptions.class;
    }

    @Override
    public void validateOptions(final Map<String, String> clientOptionsMap) {
        super.validateOptions(clientOptionsMap);

        MobeelizerTextFieldOptions options = (MobeelizerTextFieldOptions) getOptions(clientOptionsMap);

        if (options.getMaxLength() <= 0) {
            throw new IllegalStateException("Max length must be a positive integer.");
        }
    }

    @Override
    public Object parseValue(final String value, final MobeelizerFieldOptions options) {
        return value;
    }

}
