package com.twitter.kamilyedrzejuq.method;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodCallerFinder {

    public Method tryFindCallMethod() {
        int index = 3; //possible index on stacktrace
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[index];

        String declaringClass = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        Method method = tryGetMethodByName(declaringClass, methodName);
        if (isTestJunitMethod(method)) {
            return method;
        }

        return tryFindInStackTrace();
    }

    private Method tryFindInStackTrace() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int elements = stackTrace.length;
        for (int i = 0; i < elements; i++) {
            String declaringClass = stackTrace[i].getClassName();
            String methodName = stackTrace[i].getMethodName();
            Method method = tryGetMethodByName(declaringClass, methodName);
            if (isTestJunitMethod(method)) {
                return method;
            }
        }
        return null;
    }

    private boolean isTestJunitMethod(Method method) {
        if (method == null)
            return false;
        for (Annotation a : method.getAnnotations()) {
            if (a instanceof org.junit.jupiter.api.Test)
                return true;
        }
        return false;
    }

    private Method tryGetMethodByName(String declaringClass, String name) {
        try {
            return Class.forName(declaringClass).getMethod(name);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
        }
        return null;
    }
}
