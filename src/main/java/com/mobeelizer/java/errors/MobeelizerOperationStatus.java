package com.mobeelizer.java.errors;

import com.mobeelizer.java.api.MobeelizerOperationError;


public class MobeelizerOperationStatus<T> {

    private T content;

    private MobeelizerOperationError error;

    public MobeelizerOperationStatus(final T content) {
        this.content = content;
    }

    public MobeelizerOperationStatus(final MobeelizerOperationError error) {
        this.error = error;
    }

    public T getContent() {
        return content;
    }

    public MobeelizerOperationError getError() {
        return error;
    }

}
