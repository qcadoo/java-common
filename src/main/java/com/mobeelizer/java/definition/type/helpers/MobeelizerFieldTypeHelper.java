// 
// MobeelizerFieldTypeHelper.java
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

import static com.mobeelizer.java.model.MobeelizerReflectionUtil.getValue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.mobeelizer.java.definition.MobeelizerErrorsHolder;
import com.mobeelizer.java.definition.type.options.MobeelizerFieldOptions;
import com.mobeelizer.java.definition.type.options.type.MobeelizerModelFieldOption;
import com.mobeelizer.java.model.MobeelizerFieldAccessor;

public abstract class MobeelizerFieldTypeHelper {

    private final Set<Class<?>> accessibleTypes;

    private final Map<String, MobeelizerModelFieldOption> options = new HashMap<String, MobeelizerModelFieldOption>();

    public MobeelizerFieldTypeHelper(final Class<?>... accessibleTypes) {
        this.accessibleTypes = new HashSet<Class<?>>(Arrays.asList(accessibleTypes));
    }

    public Set<Class<?>> getAccessibleTypes() {
        return accessibleTypes;
    }

    public abstract Class<?> getDefaultAccessibleType();

    public abstract Object convertDefaultValue(final MobeelizerFieldAccessor field, final String defaultValue,
            final Map<String, String> options);

    public abstract String convertFromEntityValueToJsonValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerErrorsHolder errors);

    public abstract Object convertFromJsonValueToEntityValue(final MobeelizerFieldAccessor field, final String value);

    public abstract Object convertFromDatabaseValueToEntityValue(final MobeelizerFieldAccessor field, final Object value);

    public abstract Object convertFromEntityValueToDatabaseValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerErrorsHolder errors);

    public abstract boolean validateValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerErrorsHolder errors);

    public void setValueFromEntityToJsonEntity(final MobeelizerFieldAccessor field, final Object entity,
            final Map<String, String> values, final boolean required, final Map<String, String> options,
            final MobeelizerErrorsHolder errors) {
        Object value = getValue(field, entity);

        if (value == null && required) {
            errors.addFieldCanNotBeEmpty(field.getName());
            return;
        }

        if (value == null) {
            setNullValueFromEntityToJsonEntity(values, field, options, errors);
        } else {
            setNotNullFromEntityToJsonEntity(values, value, field, options, errors);
        }
    }

    public void setValueFromJsonEntityToEntity(final MobeelizerFieldAccessor field, final Map<String, String> values,
            final Map<String, String> options, final Object entity) {
        String value = values.get(field.getName());

        if (value == null) {
            setNullValueFromJsonEntityToEntity(field, options, entity);
        } else {
            setNotNullValueFromJsonEntityToEntity(field, value, options, entity);
        }
    }

    protected abstract void setNullValueFromJsonEntityToEntity(final MobeelizerFieldAccessor field,
            final Map<String, String> options2, final Object entity);

    protected abstract void setNotNullValueFromJsonEntityToEntity(final MobeelizerFieldAccessor field, final String value,
            final Map<String, String> options, final Object entity);

    protected abstract void setNotNullFromEntityToJsonEntity(final Map<String, String> values, final Object value,
            final MobeelizerFieldAccessor field, final Map<String, String> options, final MobeelizerErrorsHolder errors);

    protected abstract void setNullValueFromEntityToJsonEntity(final Map<String, String> values,
            final MobeelizerFieldAccessor field, final Map<String, String> options, final MobeelizerErrorsHolder errors);

    public abstract String validateAndNormalizeValue(final String value, final MobeelizerFieldOptions options);

    protected void addOption(final MobeelizerModelFieldOption option) {
        options.put(option.getName(), option);
    }

    protected abstract Class<? extends MobeelizerFieldOptions> getOptionObjectClass();

    public abstract Object parseValue(final String value, final MobeelizerFieldOptions options);

    public MobeelizerFieldOptions getOptions(final Map<String, String> optionsMap) {
        Class<? extends MobeelizerFieldOptions> clazz = getOptionObjectClass();

        try {
            MobeelizerFieldOptions optionsObject = clazz.newInstance();
            if (optionsMap == null) {
                return optionsObject;
            }
            for (MobeelizerModelFieldOption option : options.values()) {
                String optionValue = optionsMap.get(option.getName());
                if (optionValue != null) {
                    Field optionField = clazz.getDeclaredField(option.getName());
                    optionField.setAccessible(true);
                    optionField.set(optionsObject, option.getType().getValue(optionValue));
                }
            }

            return optionsObject;
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

    }

    public void validateOptions(final Map<String, String> clientOptionsMap) {
        Map<String, String> optionsMap = clientOptionsMap == null ? Collections.<String, String> emptyMap() : clientOptionsMap;
        for (MobeelizerModelFieldOption option : options.values()) {
            String optionValue = optionsMap.get(option.getName());
            if (optionValue == null && option.isRequired()) {
                throw new IllegalStateException("Option '" + option.getName() + "' is required.");
            } else if (optionValue != null && !option.getType().validate(optionValue)) {
                throw new IllegalStateException("Wrong value for option '" + option.getName() + "'.");
            }
        }
        for (String optionName : optionsMap.keySet()) {
            if (!options.containsKey(optionName)) {
                throw new IllegalStateException("Unknown option '" + optionName + "'.");
            }
        }
    }

}
