package com.twitter.kamilyedrzejuq.veryfier;

import com.twitter.kamilyedrzejuq.clazz.TestMethodBlocksProvider;
import com.twitter.kamilyedrzejuq.specification.Block;
import com.twitter.kamilyedrzejuq.specification.BlockCombinationNotAllowed;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Optional;

class StructureVerifier {

    private static final TestMethodBlocksProvider testMethodBlocksProvider = new TestMethodBlocksProvider();

    TestVerificationResult verifySilence(Class<?> clazz, Method method) {
        LinkedList<Block> blocks = testMethodBlocksProvider.read(clazz, method);
        TestStructureSpecification testStructureSpecification = new TestStructureSpecification(blocks);
        try {
            testStructureSpecification.test(method);
        } catch (BlockCombinationNotAllowed e) {
            TestVerificationFailed testVerificationFailed = new TestVerificationFailed(e.getMessage(), e);
            return TestVerificationResult.fail(blocks, testVerificationFailed);
        }
        return TestVerificationResult.success(blocks);
    }

    void verify(Class<?> clazz, Method method) {
        TestVerificationResult testVerificationResult = verifySilence(clazz, method);
        Optional<TestVerificationResult.Fail> fail1 = testVerificationResult.getFail();
        fail1.ifPresent(fail -> rethrow( fail.throwable));
    }

    private static RuntimeException rethrow(Throwable throwable) {
        throwAs(throwable);
        return null;
    }

    private static <T extends Throwable> void throwAs(Throwable t) throws T {
        throw (T) t;
    }
}
