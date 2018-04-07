package com.example.inikolovski.mvpsampleproject.common.util;


import javax.annotation.Nullable;

public final class Preconditions {
    private Preconditions() {
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }
}
