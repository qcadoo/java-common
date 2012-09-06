package com.mobeelizer.java.api;

import java.util.List;

/**
 * Representation of the operation error.
 * 
 * @since 1.4
 */
public interface MobeelizerOperationError {

    /**
     * Return the code of the error.
     * 
     * @return code
     * @since 1.4
     */
    String getCode();

    /**
     * Return the readable message for the error.
     * 
     * @return code
     * @since 1.4
     */
    String getMessage();

    /**
     * Return the arguments for message.
     * 
     * @return code
     * @since 1.4
     */
    List<Object> getArguments();

}
