// 
// MobeelizerDecimalFieldTypeHelper.java
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

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mobeelizer.java.api.MobeelizerErrorsBuilder;
import com.mobeelizer.java.definition.type.options.MobeelizerDecimalFieldOptions;
import com.mobeelizer.java.definition.type.options.MobeelizerFieldOptions;
import com.mobeelizer.java.definition.type.options.type.MobeelizerBooleanFieldOptionTypeHelper;
import com.mobeelizer.java.definition.type.options.type.MobeelizerDecimalFieldOptionTypeHelper;
import com.mobeelizer.java.definition.type.options.type.MobeelizerIntegerFieldOptionTypeHelper;
import com.mobeelizer.java.definition.type.options.type.MobeelizerModelFieldOption;
import com.mobeelizer.java.model.MobeelizerFieldAccessor;

public class MobeelizerDecimalFieldTypeHelper extends MobeelizerFieldTypeHelper {

    public static final String MIN_VALUE = "minValue";

    public static final String MAX_VALUE = "maxValue";

    public static final String INCLUDE_MIN_VALUE = "includeMinValue";

    public static final String INCLUDE_MAX_VALUE = "includeMaxValue";

    public static final String SCALE = "scale";

    private static final Pattern decimalPattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");

    public MobeelizerDecimalFieldTypeHelper() {
        super(Double.class, Double.TYPE, Float.class, Float.TYPE, BigDecimal.class);
        addOption(new MobeelizerModelFieldOption(MIN_VALUE, new MobeelizerDecimalFieldOptionTypeHelper(), false));
        addOption(new MobeelizerModelFieldOption(MAX_VALUE, new MobeelizerDecimalFieldOptionTypeHelper(), false));
        addOption(new MobeelizerModelFieldOption(SCALE, new MobeelizerIntegerFieldOptionTypeHelper(), false));
        addOption(new MobeelizerModelFieldOption(INCLUDE_MIN_VALUE, new MobeelizerBooleanFieldOptionTypeHelper(), false));
        addOption(new MobeelizerModelFieldOption(INCLUDE_MAX_VALUE, new MobeelizerBooleanFieldOptionTypeHelper(), false));
    }

    @Override
    public Class<?> getDefaultAccessibleType() {
        return Double.class;
    }

    @Override
    public String convertFromEntityValueToJsonValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerErrorsBuilder errors) {
        Double doubleValue = ((Number) value).doubleValue();

        if (!validateValue(field, doubleValue, options, errors)) {
            return null;
        }

        return Double.toString(doubleValue);
    }

    @Override
    public Object convertFromJsonValueToEntityValue(final MobeelizerFieldAccessor field, final String value) {
        return convertFromDatabaseValueToEntityValue(field, Double.valueOf(value));
    }

    @Override
    public Object convertFromDatabaseValueToEntityValue(final MobeelizerFieldAccessor field, final Object value) {
        Double doubleValue = (Double) value;

        if (field.getType().equals(Double.TYPE) || field.getType().equals(Double.class)) {
            return doubleValue;
        } else if (field.getType().equals(Float.TYPE) || field.getType().equals(Float.class)) {
            return doubleValue.floatValue();
        } else if (field.getType().equals(BigDecimal.class)) {
            return BigDecimal.valueOf(doubleValue);
        } else {
            throw new IllegalStateException("Cannot get decimal from '" + field.getType().getCanonicalName() + "'.");
        }
    }

    @Override
    public Object convertFromEntityValueToDatabaseValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerErrorsBuilder errors) {
        Double doubleValue = ((Number) value).doubleValue();

        if (!validateValue(field, doubleValue, options, errors)) {
            return null;
        }

        return doubleValue;
    }

    @Override
    public boolean validateValue(final MobeelizerFieldAccessor field, final Object value, final Map<String, String> options,
            final MobeelizerErrorsBuilder errors) {
        Double doubleValue = (Double) value;

        boolean includeMaxValue = getIncludeMaxValue(options);
        boolean includeMinValue = getIncludeMinValue(options);
        BigDecimal minValue = getMinValue(options);
        BigDecimal maxValue = getMaxValue(options);

        if (includeMaxValue && doubleValue > maxValue.doubleValue()) {
            errors.addFieldMustBeLessThanOrEqualTo(field.getName(), maxValue);
            return false;
        }

        if (!includeMaxValue && doubleValue >= maxValue.doubleValue()) {
            errors.addFieldMustBeLessThan(field.getName(), maxValue);
            return false;
        }

        if (includeMinValue && doubleValue < minValue.doubleValue()) {
            errors.addFieldMustBeGreaterThanOrEqual(field.getName(), minValue);
            return false;
        }

        if (!includeMinValue && doubleValue <= minValue.doubleValue()) {
            errors.addFieldMustBeGreaterThan(field.getName(), minValue);
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
                return new BigDecimal(defaultValue).setScale(getScale(options));
            } catch (ArithmeticException e) {
                throw new IllegalStateException(e.getMessage(), e);
            } catch (NumberFormatException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
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
        String normalizedValue = value.trim();

        Matcher matcher = decimalPattern.matcher(normalizedValue);

        if (!matcher.matches()) {
            throw new IllegalStateException("Illegal decimal value: " + value);
        }

        BigDecimal decimal = new BigDecimal(normalizedValue);

        MobeelizerDecimalFieldOptions decimalOptions = (MobeelizerDecimalFieldOptions) options;

        if (decimal.scale() > decimalOptions.getScale()) {
            throw new IllegalStateException("Scale for " + normalizedValue + " is larger than " + decimalOptions.getScale() + ".");
        }

        checkValueRange(normalizedValue, decimal, decimalOptions);

        decimal = decimal.setScale(decimalOptions.getScale());

        return decimal.toString();
    }

    private void checkValueRange(final String normalizedValue, final BigDecimal decimal,
            final MobeelizerDecimalFieldOptions decimalOptions) {
        if (decimal.compareTo(decimalOptions.getMinValue()) < 0
                || (decimal.compareTo(decimalOptions.getMinValue()) == 0 && !decimalOptions.isIncludeMinValue())) {
            throw new IllegalStateException("Value " + normalizedValue + " is lower than min value "
                    + decimalOptions.getMinValue() + ".");
        }

        if (decimal.compareTo(decimalOptions.getMaxValue()) > 0
                || (decimal.compareTo(decimalOptions.getMaxValue()) == 0 && !decimalOptions.isIncludeMaxValue())) {
            throw new IllegalStateException("Value " + normalizedValue + " is greater than max value "
                    + decimalOptions.getMaxValue() + ".");
        }
    }

    @Override
    protected Class<? extends MobeelizerFieldOptions> getOptionObjectClass() {
        return MobeelizerDecimalFieldOptions.class;
    }

    @Override
    public void validateOptions(final Map<String, String> clientOptionsMap) {
        super.validateOptions(clientOptionsMap);
        MobeelizerDecimalFieldOptions options = (MobeelizerDecimalFieldOptions) getOptions(clientOptionsMap);
        if (options.getMaxValue().compareTo(options.getMinValue()) < 0) {
            throw new IllegalStateException(MAX_VALUE + " is lower than " + MIN_VALUE + ".");
        }
        if (options.getMaxValue().compareTo(options.getMinValue()) == 0
                && (!options.isIncludeMinValue() || !options.isIncludeMaxValue())) {
            throw new IllegalStateException("Range is empty: " + MIN_VALUE + "-" + MAX_VALUE + ".");
        }
    }

    @Override
    public Object parseValue(final String value, final MobeelizerFieldOptions options) {
        MobeelizerDecimalFieldOptions decimalOptions = (MobeelizerDecimalFieldOptions) options;
        return new BigDecimal(value).setScale(decimalOptions.getScale());
    }

    private int getScale(final Map<String, String> options) {
        return options.containsKey("scale") ? Integer.valueOf(options.get("scale")) : 3;
    }

    private BigDecimal getMaxValue(final Map<String, String> options) {
        return options.containsKey("maxValue") ? new BigDecimal(options.get("maxValue")) : BigDecimal.valueOf(Double.MAX_VALUE);
    }

    private BigDecimal getMinValue(final Map<String, String> options) {
        return options.containsKey("minValue") ? new BigDecimal(options.get("minValue")) : BigDecimal.valueOf(-Double.MAX_VALUE);
    }

    private boolean getIncludeMinValue(final Map<String, String> options) {
        return options.containsKey("includeMinValue") ? "true".equals(options.get("includeMinValue")) : true;
    }

    private boolean getIncludeMaxValue(final Map<String, String> options) {
        return options.containsKey("includeMaxValue") ? "true".equals(options.get("includeMaxValue")) : true;
    }

}
