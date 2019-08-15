package com.twitter.kamilyedrzejuq.specification;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.function.Predicate;

class SpecificationStructureValidator {

    private final Structure structure;

    SpecificationStructureValidator(Structure structure) {
        this.structure = structure;
    }

    void test() {
        Predicate<LinkedList<Block>> containsBlocks = containsBlocks();
        String exceptionMessage = buildExcMessage("Test should contains structure with blocks: {given when, then, expect}", structure.getMethod());
        testAndThrowErrorWhenFail(containsBlocks, exceptionMessage);
    }

    private String buildExcMessage(String message, Method method) {
        return message + " method: " + method.getName();
    }

    private void testAndThrowErrorWhenFail(Predicate<LinkedList<Block>> predicate, String exceptionMessage) {
        boolean failed = !predicate.test(structure.getBlocks());
        if (failed)
            throw new SpecificationStructureException(exceptionMessage);
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
