package com.twitter.kamilyedrzejuq;

import com.twitter.kamilyedrzejuq.veryfier.TestVerificationFailed;
import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

public class TestBlockExecutionTest {

    @Test
    public void should_check_that_test_without_any_block_will_fail() {
        //when
        TestExecutionSummary summary = runTestMethod(BlockTest.class, "method_without_any_block");

        //then
        int expected_failed_tests = 1;
        long actual_failed_tests = summary.getTestsFailedCount();
        assertEquals(expected_failed_tests, actual_failed_tests);

        //and
        Class<? extends Throwable> actualException = summary.getFailures().get(0).getException().getClass();
        assertEquals(TestVerificationFailed.class, actualException);
    }

    private TestExecutionSummary runTestMethod(Class<?> testClass, String methodName) {
        SummaryGeneratingListener listener = new SummaryGeneratingListener();

        LauncherDiscoveryRequest request = request().selectors(selectMethod(testClass, methodName)).build();
        LauncherFactory.create
                ().execute(request, listener);

        return listener.getSummary();
    }
}
