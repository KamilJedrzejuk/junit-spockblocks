package com.twitter.kamilyedrzejuq.veryfier;


import com.twitter.kamilyedrzejuq.specification.Block;
import com.twitter.kamilyedrzejuq.specification.BlockCombinationNotAllowed;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.function.Predicate;

class TestStructureSpecification {

    @Data
    @AllArgsConstructor
    public static class Error {
        String message;

        static Error of(String message) {
            return new Error(message);
        }
    }

    private final LinkedList<Block> blocks;

    TestStructureSpecification(LinkedList<Block> blocks) {
        this.blocks = blocks;
    }

    void test() {
        Predicate<LinkedList<Block>> containsBlocks = containsBlocks();
        testAndThrowErrorWhenFail(containsBlocks, "Test should contains structure with blocks: {given when, then, expect}");


        //
//        firstBlockIsExpect().
//
//        Predicate<LinkedList<Block>> containsExpectAndOptionallyAndBlocks = firstBlockIsExpect().and(afterExpectBlockOnlyAndIsAllowed());
//        testAndThrowErrorWhenFail(containsExpectAndOptionallyAndBlocks, "After expect block only and blocks are allowed");

    }

    private void testAndThrowErrorWhenFail(Predicate<LinkedList<Block>> predicate, String exceptionMessage) {
        boolean failed = !predicate.test(blocks);
        if (failed)
            throw new BlockCombinationNotAllowed(exceptionMessage);

    }

    private static Predicate<LinkedList<Block>> containsBlocks() {
        return blocks -> !blocks.isEmpty();
    }

    private static Predicate<LinkedList<Block>> firstBlockIsGiven() {
        return blocks -> blocks.size() > 0 && blocks.getFirst() == Block.GIVEN;
    }

    private static Predicate<LinkedList<Block>> firstBlockIsExpect() {
        return blocks -> blocks.size() > 0 && blocks.getFirst() == Block.EXPECT;
    }

    private static Predicate<LinkedList<Block>> afterExpectBlockOnlyAndIsAllowed() {
        return blocks -> blocks.size() >= 2 && blocks.getFirst() == Block.EXPECT
                && new HashSet<>(blocks.subList(1, blocks.size() - 1)).contains(Block.AND);

    }

    private static Predicate<LinkedList<Block>> noDoubleOfPrimaryBlocks() {
        return blocks ->
                blocks.stream().filter(b -> b == Block.GIVEN).count() <= 1
                        &&
                        blocks.stream().filter(b -> b == Block.WHEN).count() <= 1
                        &&
                        blocks.stream().filter(b -> b == Block.THEN).count() <= 1
                        &&
                        blocks.stream().filter(b -> b == Block.EXPECT).count() <= 1;
    }


    static private LinkedList<Block> blocks(Block... blocks) {
        LinkedList<Block> result = new LinkedList<>();
        if (blocks == null) {
            return result;
        }
        result.addAll(Arrays.asList(blocks));
        return result;
    }
}
