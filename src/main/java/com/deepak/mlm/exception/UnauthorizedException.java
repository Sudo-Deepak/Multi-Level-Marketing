package com.deepak.mlm.exception;

/**
 * This is our UnauthorizedException class, it will be invoked when some unauthorized user will try access our end points.
 * @author Sudo-Deepak
 */
public class UnauthorizedException extends Exception{
    public UnauthorizedException() {
        super("Unauthorized user");
    }
}
