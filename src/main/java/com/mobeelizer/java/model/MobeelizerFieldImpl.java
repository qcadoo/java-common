// 
// MobeelizerFieldImpl.java
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

package com.mobeelizer.java.model;

import java.util.Map;

import com.mobeelizer.java.api.MobeelizerField;
import com.mobeelizer.java.api.MobeelizerFieldCredentials;
import com.mobeelizer.java.definition.MobeelizerErrorsHolder;
import com.mobeelizer.java.definition.MobeelizerFieldType;
import com.mobeelizer.java.definition.MobeelizerModelFieldCredentialsDefinition;
import com.mobeelizer.java.definition.MobeelizerModelFieldDefinition;

public class MobeelizerFieldImpl implements MobeelizerField {

    private final String name;

    private final boolean required;

    private final Object defaultValue;

    private final MobeelizerFieldAccessor field;

    private final MobeelizerFieldType type;

    private final Map<String, String> options;

    private final MobeelizerFieldCredentials credentials;

    public MobeelizerFieldImpl(final Class<?> clazz, final MobeelizerModelFieldDefinition field,
            final MobeelizerModelFieldCredentialsDefinition credentials) {
        this.name = field.getName();
        this.credentials = new MobeelizerFieldCredentialsImpl(credentials);
        this.options = field.getOptions();
        this.type = field.getType();
        if (clazz != null) {
            this.field = new ReflectionMobeelizerFieldAccessor(MobeelizerReflectionUtil.getField(clazz, name,
                    type.getAccessibleTypes()));
        } else {
            this.field = new BasicMobeelizerFieldAccessor(field);
        }

        this.required = field.isRequired();
        this.defaultValue = type.convertDefaultValue(this.field, field.getDefaultValue(), options);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public MobeelizerFieldCredentials getCredentials() {
        return credentials;
    }

    public void setValueFromJsonEntityToEntity(final Map<String, String> values, final Object entity) {
        type.setValueFromJsonEntityToEntity(field, values, options, entity);
    }

    public void setValueFromEntityToJsonEntity(final Object entity, final Map<String, String> values,
            final MobeelizerErrorsHolder errors) {
        type.setValueFromEntityToJsonEntity(field, entity, values, required, options, errors);
    }

    public MobeelizerFieldAccessor getField() {
        return field;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public MobeelizerFieldType getType() {
        return type;
    }

}
