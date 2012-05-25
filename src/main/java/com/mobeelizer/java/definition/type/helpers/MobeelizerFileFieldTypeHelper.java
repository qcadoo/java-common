// 
// MobeelizerFileFieldTypeHelper.java
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

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.mobeelizer.java.api.MobeelizerFile;
import com.mobeelizer.java.definition.MobeelizerErrorsHolder;
import com.mobeelizer.java.definition.type.options.MobeelizerEmptyFieldOptions;
import com.mobeelizer.java.definition.type.options.MobeelizerFieldOptions;
import com.mobeelizer.java.model.MobeelizerFieldAccessor;

public class MobeelizerFileFieldTypeHelper extends MobeelizerFieldTypeHelper {

    public MobeelizerFileFieldTypeHelper() {
        super(MobeelizerFile.class);
    }

    @Override
    public Object convertDefaultValue(final MobeelizerFieldAccessor field, final String defaultValue,
            final Map<String, String> options) {
        return null;
    }

    @Override
    public Class<?> getDefaultAccessibleType() {
        return MobeelizerFile.class;
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

    private static final Pattern uuidPattern = Pattern
            .compile("^([0-9a-fA-F]){8}-([0-9a-fA-F]){4}-([0-9a-fA-F]){4}-([0-9a-fA-F]){4}-([0-9a-fA-F]){12}");

    @Override
    public String validateAndNormalizeValue(final String value, final MobeelizerFieldOptions options) {
        MobeelizerFileValue fileValue = new MobeelizerFileValue(value);

        if (fileValue.getName() == null || "".equals(fileValue.getName().trim())) {
            throw new IllegalStateException("Missing filename.");
        }

        if (fileValue.getGuid() == null || !uuidPattern.matcher(fileValue.getGuid()).matches()) {
            throw new IllegalStateException("Illegal guid value: " + fileValue.getGuid());
        }

        return fileValue.toJson();
    }

    @Override
    protected Class<? extends MobeelizerFieldOptions> getOptionObjectClass() {
        return MobeelizerEmptyFieldOptions.class;
    }

    @Override
    public Object parseValue(final String value, final MobeelizerFieldOptions options) {
        return new MobeelizerFileValue(value);
    }

    public static class MobeelizerFileValue implements MobeelizerFile {

        private String guid;

        private String name;

        public MobeelizerFileValue() {
            // empty
        }

        private MobeelizerFileValue(final String json) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.length() != 2) {
                    throw new IllegalStateException("Filename and guid are required.");
                }
                guid = jsonObject.getString("guid").trim();
                name = jsonObject.getString("filename");
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override
        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        @Override
        public String getGuid() {
            return guid;
        }

        public void setGuid(final String guid) {
            this.guid = guid.trim();
        }

        @Override
        public InputStream getInputStream() {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public File getFile() {
            throw new UnsupportedOperationException("Not implemented.");
        }

        private String toJson() {
            try {
                return new JSONStringer().object().key("filename").value(name).key("guid").value(guid).endObject().toString();
            } catch (JSONException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }

    }

    @Override
    public String convertFromEntityValueToJsonValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerErrorsHolder errors) {
        MobeelizerFile file = (MobeelizerFile) value;

        if (!validateValue(field, file, options, errors)) {
            return null;
        }

        JSONObject json = new JSONObject();
        try {
            json.put("guid", file.getGuid());
            json.put("filename", file.getName());
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        return json.toString();
    }

    @Override
    public Object convertFromJsonValueToEntityValue(final MobeelizerFieldAccessor field, final String value) {
        return new MobeelizerFileValue(value);
    }

    @Override
    public Object convertFromDatabaseValueToEntityValue(final MobeelizerFieldAccessor field, final Object value) {
        String[] fileValue = (String[]) value;

        MobeelizerFileValue file = new MobeelizerFileValue();
        file.setGuid(fileValue[0]);
        file.setName(fileValue[1]);

        return file;
    }

    @Override
    public Object convertFromEntityValueToDatabaseValue(final MobeelizerFieldAccessor field, final Object value,
            final Map<String, String> options, final MobeelizerErrorsHolder errors) {
        MobeelizerFile file = (MobeelizerFile) value;

        if (!validateValue(field, file, options, errors)) {
            return null;
        }

        return file;
    }

    @Override
    public boolean validateValue(final MobeelizerFieldAccessor field, final Object value, final Map<String, String> options,
            final MobeelizerErrorsHolder errors) {
        return true;
    }

}
