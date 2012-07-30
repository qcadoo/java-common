// 
// MobeelizerDateFieldTypeHelper.java
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

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import com.mobeelizer.java.api.MobeelizerErrorsBuilder;
import com.mobeelizer.java.definition.type.options.MobeelizerEmptyFieldOptions;
import com.mobeelizer.java.definition.type.options.MobeelizerFieldOptions;
import com.mobeelizer.java.model.MobeelizerFieldAccessor;

public class MobeelizerDateFieldTypeHelper extends MobeelizerFieldTypeHelper {

    private static final Pattern longPattern = Pattern.compile("-?[0-9]+");

    public MobeelizerDateFieldTypeHelper() {
        super(Date.class, Long.class, Long.TYPE, Calendar.class);
    }

    @Override
    public Class<?> getDefaultAccessibleType() {
        return Date.class;
    }

    @Override
    public String convertFromEntityValueToJsonValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerErrorsBuilder errors) {
        Long longValue = null;

        if (value instanceof Date) {
            longValue = ((Date) value).getTime();
        } else if (value instanceof Calendar) {
            longValue = ((Calendar) value).getTime().getTime();
        } else {
            longValue = ((Number) value).longValue();
        }

        if (!validateValue(field, longValue, options, errors)) {
            return null;
        }

        return Long.toString(longValue);
    }

    @Override
    public Object convertFromJsonValueToEntityValue(final MobeelizerFieldAccessor field, final String value) {
        return convertFromDatabaseValueToEntityValue(field, Long.valueOf(value));
    }

    @Override
    public Object convertFromDatabaseValueToEntityValue(final MobeelizerFieldAccessor field, final Object value) {
        long longValue = (Long) value;

        if (field.getType().equals(Long.TYPE) || field.getType().equals(Long.class)) {
            return longValue;
        } else if (field.getType().equals(Date.class)) {
            return new Date(longValue);
        } else if (field.getType().equals(Calendar.class)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(longValue));
            return calendar;
        } else {
            throw new IllegalStateException("Cannot get date from '" + field.getType().getCanonicalName() + "'.");
        }
    }

    @Override
    public boolean validateValue(final MobeelizerFieldAccessor field, final Object value, final Map<String, String> options,
            final MobeelizerErrorsBuilder errors) {
        return true;
    }

    @Override
    public Object convertFromEntityValueToDatabaseValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerErrorsBuilder errors) {
        Long longValue = null;

        if (value instanceof Date) {
            longValue = ((Date) value).getTime();
        } else if (value instanceof Calendar) {
            longValue = ((Calendar) value).getTime().getTime();
        } else {
            longValue = ((Number) value).longValue();
        }

        if (!validateValue(field, longValue, options, errors)) {
            return null;
        }

        return longValue;
    }

    @Override
    public Object convertDefaultValue(final MobeelizerFieldAccessor field, final String defaultValue,
            final Map<String, String> options) {
        if (defaultValue == null) {
            return null;
        } else {
            try {
                return new Date(Long.parseLong(defaultValue));
            } catch (NumberFormatException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
    }

    @Override
    protected void setNotNullFromEntityToJsonEntity(final Map<String, String> values, final Object value,
            final MobeelizerFieldAccessor field, final Map<String, String> options,
            final MobeelizerErrorsBuilder errors) {
        String stringValue = convertFromEntityValueToJsonValue(field, value, options, errors);

        if (!errors.hasNoErrors()) {
            return;
        }

        values.put(field.getName(), stringValue);
    }

    @Override
    protected void setNullValueFromEntityToJsonEntity(final Map<String, String> values, final MobeelizerFieldAccessor field,
            final Map<String, String> options, final MobeelizerErrorsBuilder errors) {
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

    @Override
    public String validateAndNormalizeValue(final String value, final MobeelizerFieldOptions options) {
        String normalizedValue = value.trim();

        if (!longPattern.matcher(normalizedValue).matches()) {
            throw new IllegalStateException("Illegal date value: " + value);
        }

        return Long.valueOf(normalizedValue).toString();
    }

    @Override
    protected Class<? extends MobeelizerFieldOptions> getOptionObjectClass() {
        return MobeelizerEmptyFieldOptions.class;
    }

    @Override
    public Object parseValue(final String value, final MobeelizerFieldOptions options) {
        return new Date(Long.valueOf(value));
    }

}
