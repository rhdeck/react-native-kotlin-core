package com.rnk.core

@FunctionalInterface
public interface EventHandler {
    boolean execute(Object data);
}