package com.ebanma.cloud.rpc.utils;

public class String2Class {

    public static Class<?>[] string2Class(String[] parameterTypeStrings) throws Exception {
        Class<?>[] parameterTypes = new Class<?>[parameterTypeStrings.length];
        for (int i = 0; i < parameterTypeStrings.length; i++) {
            parameterTypes[i] = Class.forName(parameterTypeStrings[i]);
        }
        return parameterTypes;
    }
}