package com.twitter.kamilyedrzejuq.extension;

import com.twitter.kamilyedrzejuq.specification.SpecificationVerifier;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class Verifier implements AfterEachCallback {

    static private final SpecificationVerifier verifier = new SpecificationVerifier();

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        context.getTestMethod().ifPresent(method -> {
            verifier.verifyMethod(method);
        });
    }
}
