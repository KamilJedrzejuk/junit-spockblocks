package com.twitter.kamilyedrzejuq.specification;

import com.twitter.kamilyedrzejuq.veryfier.ClassMethodStructureVerifier;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;

class Verifier implements AfterEachCallback {

    static private final ClassMethodStructureVerifier verifier = new ClassMethodStructureVerifier();

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        context.getTestMethod().ifPresent(method -> {
            Method[] methods = new Method[1];
            methods[0] = method;
            verifier.verify(methods);
        });
    }
}
