// 
// MobeelizerBooleanFieldTypeHelper.java
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

import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import com.mobeelizer.java.api.MobeelizerErrorsBuilder;
import com.mobeelizer.java.definition.type.options.MobeelizerEmptyFieldOptions;
import com.mobeelizer.java.definition.type.options.MobeelizerFieldOptions;
import com.mobeelizer.java.model.MobeelizerFieldAccessor;

public class MobeelizerBooleanFieldTypeHelper extends MobeelizerFieldTypeHelper {

    private static final Pattern booleanPattern = Pattern.compile("true|false");

    public MobeelizerBooleanFieldTypeHelper() {
        super(Boolean.class, Boolean.TYPE);
    }

    @Override
    public Class<?> getDefaultAccessibleType() {
        return Boolean.class;
    }

    @Override
    public String convertFromEntityValueToJsonValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerErrorsBuilder errors) {
        return ((Boolean) value) ? "true" : "false";
    }

    @Override
    public Object convertFromJsonValueToEntityValue(final MobeelizerFieldAccessor field, final String value) {
        return Boolean.valueOf(value);
    }

    @Override
    public Object convertFromDatabaseValueToEntityValue(final MobeelizerFieldAccessor field, final Object value) {
        return ((Integer) value) == 1;
    }

    @Override
    public Object convertFromEntityValueToDatabaseValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerErrorsBuilder errors) {
        return ((Boolean) value) ? 1 : 0;
    }

    @Override
    public boolean validateValue(final MobeelizerFieldAccessor field, final Object value, final Map<String, String> options,
            final MobeelizerErrorsBuilder errors) {
        return true;
    }

    @Override
    public Object convertDefaultValue(final MobeelizerFieldAccessor field, final String defaultValue,
            final Map<String, String> options) {
        if (defaultValue == null) {
            return null;
        } else if ("true".equals(defaultValue)) {
            return true;
        } else if ("false".equals(defaultValue)) {
            return false;
        } else {
            throw new IllegalStateException("Invalid default value '" + defaultValue + "' for Boolean type.");
        }
    }

    @Override
    protected void setNotNullFromEntityToJsonEntity(final Map<String, String> values, final Object value,
            final MobeelizerFieldAccessor field, final Map<String, String> options, final MobeelizerErrorsBuilder errors) {
        String stringValue = convertFromEntityValueToJsonValue(field, value, options, errors);

        if (!errors.hasNoErrors()) {
            return;
        }

        values.put(field.getName(), stringValue);
    }

    @Override
    protected void setNullValueFromEntityToJsonEntity(final Map<String, String> values, final MobeelizerFieldAccessor field,
            final Map<String, String> options, final MobeelizerErrorsBuilder errors) {
        values.put(field.getName(), null);
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

    @Override
    public String validateAndNormalizeValue(final String value, final MobeelizerFieldOptions options) {
        String normalizedValue = value.trim().toLowerCase(Locale.ENGLISH);

        if (!booleanPattern.matcher(normalizedValue).matches()) {
            throw new IllegalStateException("Illegal boolean value: " + value);
        }

        return normalizedValue;
    }

    @Override
    protected Class<? extends MobeelizerFieldOptions> getOptionObjectClass() {
        return MobeelizerEmptyFieldOptions.class;
    }

    @Override
    public Object parseValue(final String value, final MobeelizerFieldOptions options) {
        return Boolean.valueOf(value);
    }

}
