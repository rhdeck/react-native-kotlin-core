package com.rnk.core;

@FunctionalInterface
public interface RNKEventHandler {
    boolean execute(Object data);
}