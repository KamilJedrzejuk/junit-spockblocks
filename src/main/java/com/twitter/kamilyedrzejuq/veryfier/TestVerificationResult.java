package com.twitter.kamilyedrzejuq.veryfier;

import com.twitter.kamilyedrzejuq.specification.Block;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class TestVerificationResult {

    private List<Block> blocks;
    private boolean success;
    private Fail fail;

    private TestVerificationResult(List<Block> methodBlocks) {
        this.blocks = requireNonNull(methodBlocks, "method blocks cannot be null");
        success = true;
        this.fail = null;
    }

    private TestVerificationResult(List<Block> methodBlocks, Throwable throwable) {
        this.blocks = requireNonNull(methodBlocks, "method blocks cannot be null");
        success = false;
        this.fail = new Fail(throwable);
    }

    static TestVerificationResult success(List<Block> methodBlocks) {
        return new TestVerificationResult(methodBlocks);
    }

    static TestVerificationResult fail(List<Block> methodBlocks, Throwable throwable) {
        return new TestVerificationResult(methodBlocks, throwable);
    }

    @Getter
    @AllArgsConstructor
    public static class Fail {
        Throwable throwable;
    }

    public List<Block> getBlocks() {
        return Collections.unmodifiableList(blocks);
    }

    public boolean isSuccess() {
        return success;
    }

    public Optional<Fail> getFail() {
        return Optional.ofNullable(fail);
    }
}
