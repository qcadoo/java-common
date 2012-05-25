// 
// MobeelizerIntegerFieldTypeHelper.java
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

import java.math.BigInteger;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mobeelizer.java.definition.MobeelizerErrorsHolder;
import com.mobeelizer.java.definition.type.options.MobeelizerFieldOptions;
import com.mobeelizer.java.definition.type.options.MobeelizerIntegerFieldOptions;
import com.mobeelizer.java.definition.type.options.type.MobeelizerIntegerFieldOptionTypeHelper;
import com.mobeelizer.java.definition.type.options.type.MobeelizerModelFieldOption;
import com.mobeelizer.java.model.MobeelizerFieldAccessor;

public class MobeelizerIntegerFieldTypeHelper extends MobeelizerFieldTypeHelper {

    public static final String MIN_VALUE = "minValue";

    public static final String MAX_VALUE = "maxValue";

    private static final Pattern integerPattern = Pattern.compile("-?[0-9]+");

    public MobeelizerIntegerFieldTypeHelper() {
        super(Integer.class, Integer.TYPE, Short.class, Short.TYPE, Byte.class, Byte.TYPE, Long.class, Long.TYPE,
                BigInteger.class);
        addOption(new MobeelizerModelFieldOption(MIN_VALUE, new MobeelizerIntegerFieldOptionTypeHelper(), false));
        addOption(new MobeelizerModelFieldOption(MAX_VALUE, new MobeelizerIntegerFieldOptionTypeHelper(), false));
    }

    @Override
    public Class<?> getDefaultAccessibleType() {
        return Integer.class;
    }

    @Override
    public String convertFromEntityValueToJsonValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerErrorsHolder errors) {
        Long longValue = ((Number) value).longValue();

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
        Long longValue = (Long) value;

        if (field.getType().equals(Integer.TYPE) || field.getType().equals(Integer.class)) {
            checkRange(longValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
            return longValue.intValue();
        } else if (field.getType().equals(Short.TYPE) || field.getType().equals(Short.class)) {
            checkRange(longValue, Short.MIN_VALUE, Short.MAX_VALUE);
            return longValue.shortValue();
        } else if (field.getType().equals(Long.TYPE) || field.getType().equals(Long.class)) {
            return longValue;
        } else if (field.getType().equals(Byte.TYPE) || field.getType().equals(Byte.class)) {
            checkRange(longValue, Byte.MIN_VALUE, Byte.MAX_VALUE);
            return longValue.byteValue();
        } else if (field.getType().equals(BigInteger.class)) {
            return BigInteger.valueOf(longValue);
        } else {
            throw new IllegalStateException("Cannot get integer from '" + field.getType().getCanonicalName() + "'.");
        }
    }

    @Override
    public Object convertFromEntityValueToDatabaseValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerErrorsHolder errors) {
        Long longValue = ((Number) value).longValue();

        if (!validateValue(field, longValue, options, errors)) {
            return null;
        }

        return longValue;
    }

    @Override
    public boolean validateValue(final MobeelizerFieldAccessor field, final Object value, final Map<String, String> options,
            final MobeelizerErrorsHolder errors) {
        Long longValue = ((Number) value).longValue();

        int maxValue = getMaxValue(options);
        int minValue = getMinValue(options);

        if (longValue > maxValue) {
            errors.addFieldMustBeLessThan(field.getName(), (long) maxValue);
            return false;
        }

        if (longValue < minValue) {
            errors.addFieldMustBeGreaterThan(field.getName(), (long) minValue);
            return false;
        }

        return true;
    }

    @Override
    public Object convertDefaultValue(final MobeelizerFieldAccessor field, final String defaultValue,
            final Map<String, String> options) {
        if (defaultValue == null) {
            return null;
        } else {
            try {
                return Long.parseLong(defaultValue);
            } catch (NumberFormatException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
    }

    @Override
    protected void setNotNullFromEntityToJsonEntity(final Map<String, String> values, final Object value,
            final MobeelizerFieldAccessor field, final Map<String, String> options, final MobeelizerErrorsHolder errors) {
        String stringValue = convertFromEntityValueToJsonValue(field, value, options, errors);

        if (!errors.isValid()) {
            return;
        }

        values.put(field.getName(), stringValue);
    }

    @Override
    protected void setNullValueFromEntityToJsonEntity(final Map<String, String> values, final MobeelizerFieldAccessor field,
            final Map<String, String> options, final MobeelizerErrorsHolder errors) {
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

    private void checkRange(final long value, final int minValue, final int maxValue) {
        if (value < minValue || value > maxValue) {
            throw new IllegalStateException("Value " + value + " out of range <" + minValue + "," + maxValue + ">.");
        }
    }

    private int getMaxValue(final Map<String, String> options) {
        return options.containsKey("maxValue") ? Integer.valueOf(options.get("maxValue")) : Integer.MAX_VALUE;
    }

    private int getMinValue(final Map<String, String> options) {
        return options.containsKey("minValue") ? Integer.valueOf(options.get("minValue")) : Integer.MIN_VALUE;
    }

    @Override
    public String validateAndNormalizeValue(final String value, final MobeelizerFieldOptions options) {
        String normalizedValue = value.trim();

        Matcher matcher = integerPattern.matcher(normalizedValue);

        if (!matcher.matches()) {
            throw new IllegalStateException("Illegal integer value: " + value);
        }

        Integer integer = Integer.valueOf(normalizedValue);

        MobeelizerIntegerFieldOptions integerOptions = (MobeelizerIntegerFieldOptions) options;

        if (integer > integerOptions.getMaxValue() || integer < integerOptions.getMinValue()) {
            throw new IllegalStateException("Value " + integer + " out of range [" + integerOptions.getMinValue() + "-"
                    + integerOptions.getMaxValue() + "].");
        }

        return integer.toString();
    }

    @Override
    protected Class<? extends MobeelizerFieldOptions> getOptionObjectClass() {
        return MobeelizerIntegerFieldOptions.class;
    }

    @Override
    public void validateOptions(final Map<String, String> clientOptionsMap) {
        super.validateOptions(clientOptionsMap);
        MobeelizerIntegerFieldOptions options = (MobeelizerIntegerFieldOptions) getOptions(clientOptionsMap);
        if (options.getMaxValue() < options.getMinValue()) {
            throw new IllegalStateException(MAX_VALUE + " is lower than " + MIN_VALUE + ".");
        }
    }

    @Override
    public Object parseValue(final String value, final MobeelizerFieldOptions options) {
        return Integer.valueOf(value);
    }

}
