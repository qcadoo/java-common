package com.mobeelizer.java.model;

import java.util.Map;

import com.mobeelizer.java.definition.MobeelizerModelFieldDefinition;

public class BasicMobeelizerFieldAccessor implements MobeelizerFieldAccessor {

    private final String name;

    private final Class<?> clazz;

    public BasicMobeelizerFieldAccessor(final MobeelizerModelFieldDefinition field) {
        name = field.getName();
        clazz = field.getType().getDefaultAccessibleType();
    }

    public BasicMobeelizerFieldAccessor(final String string, final Class<?> clazz) {
        this.name = string;
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<?> getType() {
        return clazz;
    }

    @Override
    public Object get(final Object object) {
        return getArgAsMap(object).get(name);
    }

    @Override
    public void set(final Object object, final Object value) {
        getArgAsMap(object).put(name, value);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getArgAsMap(final Object object) {
        if (!(object instanceof Map)) {
            throw new IllegalStateException("Object should be map");
        }
        return (Map<String, Object>) object;
    }

}
