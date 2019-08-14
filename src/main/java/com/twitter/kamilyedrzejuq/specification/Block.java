package com.twitter.kamilyedrzejuq.specification;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Reprensets a possible blocks in test methods.
 * Each block have a corresponding byte code instruction for a static method from {@link Specification} class.
 * NOTE!!!
 * Actually is a prefix of instruction, the instruction has also information about
 * argument and return type like below:
 * <pre>
 * {@code
 *   INVOKESTATIC com/twitter/kamilyedrzejuq/BlockTest.GIVEN (Ljava/lang/String;)V
 * }
 * or without argument:
 * {@code
 *   INVOKESTATIC com/twitter/kamilyedrzejuq/BlockTest.GIVEN ()V
 * }
 * </pre>
 */
public enum  Block {

    GIVEN("INVOKESTATIC com/twitter/kamilyedrzejuq/BlockTest.GIVEN"),
    WHEN("INVOKESTATIC com/twitter/kamilyedrzejuq/BlockTest.WHEN"),
    THEN("INVOKESTATIC com/twitter/kamilyedrzejuq/BlockTest.THEN"),
    EXPECT("INVOKESTATIC com/twitter/kamilyedrzejuq/BlockTest.EXPECT"),
    AND("INVOKESTATIC com/twitter/kamilyedrzejuq/BlockTest.AND");

    private final String byteCodeInstruction;
    Block(String instruction) {
        byteCodeInstruction = instruction;
    }

    public static Optional<Block> matchWithInstruction(String code) {
        return Stream.of(Block.values())
                .filter(b -> code.contains(b.byteCodeInstruction))
                .findFirst();
    }
}
