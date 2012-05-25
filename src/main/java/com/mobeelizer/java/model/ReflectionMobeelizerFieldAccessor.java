package com.mobeelizer.java.model;

import java.lang.reflect.Field;

public class ReflectionMobeelizerFieldAccessor implements MobeelizerFieldAccessor {

    private final Field field;

    public ReflectionMobeelizerFieldAccessor(final Field field) {
        this.field = field;
    }

    @Override
    public String getName() {
        return field.getName();
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }

    @Override
    public Object get(final Object entity) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public void set(final Object entity, final Object value) {
        try {
            field.set(entity, value);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

}
