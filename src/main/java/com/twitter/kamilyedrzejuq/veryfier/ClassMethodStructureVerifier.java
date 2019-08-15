package com.twitter.kamilyedrzejuq.veryfier;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;

public class ClassMethodStructureVerifier {

    private final StructureVerifier structureVerifier = new StructureVerifier();

    public void verify() {
        Method method = tryFindCallMethod();
        if (method != null) {
            Class<?> clazz = method.getDeclaringClass();
            structureVerifier.verify(clazz, method);
        }
    }

    public TestVerificationResult verifySilence() {
        Method method = tryFindCallMethod();
        if (method != null) {
            Class<?> clazz = method.getDeclaringClass();
            return structureVerifier.verifySilence(clazz, method);
        }
        return TestVerificationResult.fail(Collections.emptyList(), null);
    }

    private Method tryFindCallMethod() {
        int index = 3; //possible index on stacktrace
        String declaringClass = Thread.currentThread().getStackTrace()[index].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[index].getMethodName();
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
