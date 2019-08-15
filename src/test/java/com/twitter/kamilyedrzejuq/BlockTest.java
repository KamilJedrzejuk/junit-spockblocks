package com.twitter.kamilyedrzejuq;


import com.twitter.kamilyedrzejuq.specification.Block;
import com.twitter.kamilyedrzejuq.specification.Specification;
import com.twitter.kamilyedrzejuq.specification.SpecificationVerifier;
import com.twitter.kamilyedrzejuq.specification.Structure;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class BlockTest extends Specification {

    SpecificationVerifier specificationVerifier = new SpecificationVerifier();

    @Test
    public void should_get_proper_blocks_from_method() {

        GIVEN();
        //code omitted

        WHEN();
        //code omitted

        THEN();
        //code omitted

        Optional<Structure> structureBlockOfTestMethod = specificationVerifier.getStructureBlockOfTestMethod();
        LinkedList<Block> blocks = structureBlockOfTestMethod.map(Structure::getBlocks).orElse(new LinkedList<>());

        assertIterableEquals(list(Block.GIVEN, Block.WHEN, Block.THEN), blocks);
    }

    @Test
    @Disabled
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
