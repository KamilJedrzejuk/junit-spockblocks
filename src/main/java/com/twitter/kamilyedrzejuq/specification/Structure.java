package com.twitter.kamilyedrzejuq.specification;

import lombok.NonNull;
import lombok.Value;

import java.lang.reflect.Method;
import java.util.LinkedList;

@Value
public class Structure {
    @NonNull Method method;
    @NonNull LinkedList<Block> blocks;
}
