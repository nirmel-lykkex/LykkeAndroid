package com.lykkex.LykkeWallet.rest;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by e.kazimirova on 17.02.2016.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface TIMEOUT {
    int value();
}
