// 
// MobeelizerFieldType.java
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

package com.mobeelizer.java.definition;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import com.mobeelizer.java.definition.type.helpers.MobeelizerBelongsToFieldTypeHelper;
import com.mobeelizer.java.definition.type.helpers.MobeelizerBooleanFieldTypeHelper;
import com.mobeelizer.java.definition.type.helpers.MobeelizerDateFieldTypeHelper;
import com.mobeelizer.java.definition.type.helpers.MobeelizerDecimalFieldTypeHelper;
import com.mobeelizer.java.definition.type.helpers.MobeelizerFieldTypeHelper;
import com.mobeelizer.java.definition.type.helpers.MobeelizerFileFieldTypeHelper;
import com.mobeelizer.java.definition.type.helpers.MobeelizerIntegerFieldTypeHelper;
import com.mobeelizer.java.definition.type.helpers.MobeelizerTextFieldTypeHelper;
import com.mobeelizer.java.definition.type.options.MobeelizerFieldOptions;

public enum MobeelizerFieldType {

    TEXT(new MobeelizerTextFieldTypeHelper()), INTEGER(new MobeelizerIntegerFieldTypeHelper()), BOOLEAN(
            new MobeelizerBooleanFieldTypeHelper()), DECIMAL(new MobeelizerDecimalFieldTypeHelper()), DATE(
            new MobeelizerDateFieldTypeHelper()), BELONGS_TO(new MobeelizerBelongsToFieldTypeHelper()), FILE(
            new MobeelizerFileFieldTypeHelper());

    private final MobeelizerFieldTypeHelper helper;

    private MobeelizerFieldType(final MobeelizerFieldTypeHelper helper) {
        this.helper = helper;
    }

    public Set<Class<?>> getAccessibleTypes() {
        return helper.getAccessibleTypes();
    }

    public Object convertDefaultValue(final Field field, final String defaultValue, final Map<String, String> options) {
        return helper.convertDefaultValue(field, defaultValue, options);
    }

    public void setValueFromJsonEntityToEntity(final Field field, final Map<String, String> values,
            final Map<String, String> options, final Object entity) {
        helper.setValueFromJsonEntityToEntity(field, values, options, entity);
    }

    public void setValueFromEntityToJsonEntity(final Field field, final Object entity, final Map<String, String> values,
            final boolean required, final Map<String, String> options, final MobeelizerErrorsHolder errors) {
        helper.setValueFromEntityToJsonEntity(field, entity, values, required, options, errors);
    }

    public String validateAndNormalizeValue(final String value, final MobeelizerFieldOptions options) {
        return helper.validateAndNormalizeValue(value, options);
    }

    public MobeelizerFieldOptions getOptions(final Map<String, String> optionsMap) {
        return helper.getOptions(optionsMap);
    }

    public void validateOptions(final Map<String, String> optionsMap) {
        helper.validateOptions(optionsMap);
    }

    public Object parseValue(final String value, final MobeelizerFieldOptions options) {
        return helper.parseValue(value, options);
    }

    public String convertFromEntityValueToJsonValue(final Field field, final Object value, final Map<String, String> options,
            final MobeelizerErrorsHolder errors) {
        return helper.convertFromEntityValueToJsonValue(field, value, options, errors);
    }

    public Object convertFromJsonValueToEntityValue(final Field field, final String value) {
        return helper.convertFromJsonValueToEntityValue(field, value);
    }

    public Object convertFromDatabaseValueToEntityValue(final Field field, final Object value) {
        return helper.convertFromDatabaseValueToEntityValue(field, value);
    }

    public Object convertFromEntityValueToDatabaseValue(final Field field, final Object value, final Map<String, String> options,
            final MobeelizerErrorsHolder errors) {
        return helper.convertFromEntityValueToDatabaseValue(field, value, options, errors);
    }

}
