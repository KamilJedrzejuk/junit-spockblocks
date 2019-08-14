package com.twitter.kamilyedrzejuq;


import com.twitter.kamilyedrzejuq.clazz.TestMethodBlocksProvider;
import com.twitter.kamilyedrzejuq.specification.Block;
import com.twitter.kamilyedrzejuq.specification.Specification;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BlockTest extends Specification {

    TestMethodBlocksProvider testMethodBlocksProvider = new TestMethodBlocksProvider();

    @Test
    public void should_get_proper_blocks_from_method() {

        GIVEN();
        //code omitted

        WHEN();
        //code omitted

        THEN();
        //code omitted

        class Local {}
        Method currentMethod = Local.class.getEnclosingMethod();
        LinkedList<Block> blocks = testMethodBlocksProvider.read(BlockTest.class, currentMethod);

        assertIterableEquals(list(Block.GIVEN, Block.WHEN, Block.THEN), blocks);
    }

    private List<Block> list(Block... blocks) {
        if(blocks == null)
            return Collections.emptyList();
        List<Block> result = new LinkedList<>();
        result.addAll(Arrays.asList(blocks));
        return result;
    }
}
