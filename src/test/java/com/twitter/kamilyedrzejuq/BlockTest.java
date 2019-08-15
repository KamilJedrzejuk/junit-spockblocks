package com.twitter.kamilyedrzejuq;


import com.twitter.kamilyedrzejuq.specification.Block;
import com.twitter.kamilyedrzejuq.specification.Specification;
import com.twitter.kamilyedrzejuq.veryfier.ClassMethodStructureVerifier;
import com.twitter.kamilyedrzejuq.veryfier.TestVerificationResult;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class BlockTest extends Specification {

    ClassMethodStructureVerifier methodStructureVerifier = new ClassMethodStructureVerifier();

    @Test
    public void should_get_proper_blocks_from_method() {

        GIVEN();
        //code omitted

        WHEN();
        //code omitted

        THEN();
        //code omitted

        TestVerificationResult verificationResult = methodStructureVerifier.verifySilence();

        assertIterableEquals(list(Block.GIVEN, Block.WHEN, Block.THEN), verificationResult.getBlocks());
    }

    @Test
    public void method_without_any_block() {

    }

    private List<Block> list(Block... blocks) {
        if (blocks == null)
            return Collections.emptyList();
        List<Block> result = new LinkedList<>();
        result.addAll(Arrays.asList(blocks));
        return result;
    }
}
