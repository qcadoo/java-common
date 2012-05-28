package com.mobeelizer.java.model;

public interface MobeelizerFieldAccessor {

    public String getName();

    public Class<?> getType();

    public Object get(final Object entity);

    public void set(final Object entity, final Object value);

}
