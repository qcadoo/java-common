// 
// MobeelizerBelongsToFieldTypeHelper.java
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

import com.mobeelizer.java.api.MobeelizerErrorsBuilder;
import com.mobeelizer.java.definition.type.options.MobeelizerBelongsToFieldOptions;
import com.mobeelizer.java.definition.type.options.MobeelizerFieldOptions;
import com.mobeelizer.java.definition.type.options.type.MobeelizerBelongsToFieldOptionTypeHelper;
import com.mobeelizer.java.definition.type.options.type.MobeelizerBooleanFieldOptionTypeHelper;
import com.mobeelizer.java.definition.type.options.type.MobeelizerModelFieldOption;
import com.mobeelizer.java.model.MobeelizerFieldAccessor;
import com.mobeelizer.java.util.ValidationUtil;

public class MobeelizerBelongsToFieldTypeHelper extends MobeelizerFieldTypeHelper {

    public static final String ANALYZE_CONFLICT = "analyzeConflict";

    public static final String MODEL = "model";

    public MobeelizerBelongsToFieldTypeHelper() {
        super(String.class); // TODO V3 auto proxy on belongsTo fields -
                             // Object.class
        addOption(new MobeelizerModelFieldOption(ANALYZE_CONFLICT, new MobeelizerBooleanFieldOptionTypeHelper(), false));
        addOption(new MobeelizerModelFieldOption(MODEL, new MobeelizerBelongsToFieldOptionTypeHelper(), true));
    }

    @Override
    public Class<?> getDefaultAccessibleType() {
        return String.class;
    }

    @Override
    public String convertFromEntityValueToJsonValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerErrorsBuilder errors) {
        if (!validateValue(field, value, options, errors)) {
            return null;
        }

        return (String) value;
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
            final Map<String, String> options, final MobeelizerErrorsBuilder errors) {
        if (!validateValue(field, value, options, errors)) {
            return null;
        }

        return value;
    }

    @Override
    public boolean validateValue(final MobeelizerFieldAccessor field, final Object value, final Map<String, String> options,
            final MobeelizerErrorsBuilder errors) {
        return true;
    }

    @Override
    public Object convertDefaultValue(final MobeelizerFieldAccessor field, final String defaultValue,
            final Map<String, String> options) {
        return null;
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
        if (!ValidationUtil.isValidGuid(value)) {
            throw new IllegalStateException("Illegal guid value: " + value);
        }

        // TODO V2 check if guid points to existing entity of proper model:
        // options.getModel()

        return value;
    }

    @Override
    protected Class<? extends MobeelizerFieldOptions> getOptionObjectClass() {
        return MobeelizerBelongsToFieldOptions.class;
    }

    @Override
    public Object parseValue(final String value, final MobeelizerFieldOptions options) {
        return value;
    }

}
