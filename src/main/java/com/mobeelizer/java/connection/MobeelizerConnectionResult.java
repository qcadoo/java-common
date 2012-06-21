package com.mobeelizer.java.connection;

public class MobeelizerConnectionResult {

    private final boolean success;

    private final String message;

    private MobeelizerConnectionResult(final boolean success, final String message) {
        this.success = success;
        this.message = message;
    }

    public static MobeelizerConnectionResult success() {
        return new MobeelizerConnectionResult(true, null);
    }

    public static MobeelizerConnectionResult failure(final String cause) {
        return new MobeelizerConnectionResult(false, cause);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
