package com.twitter.kamilyedrzejuq.specification;

import com.twitter.kamilyedrzejuq.method.MethodCallerFinder;
import com.twitter.kamilyedrzejuq.method.MethodInstruction;
import com.twitter.kamilyedrzejuq.method.MethodInstructionsProvider;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpecificationVerifier {

    private final MethodInstructionsProvider methodInstructionsProvider = new MethodInstructionsProvider();
    private final MethodCallerFinder methodCallerFinder = new MethodCallerFinder();

    public Optional<Structure> getStructureBlockOfTestMethod() {
        Method callerMethod = methodCallerFinder.tryFindCallMethod();
        if(callerMethod != null) {
            return getStructureOfMethod(callerMethod);
        }
        return Optional.empty();
    }

    public void verifyMethod(Method method) {
        Optional<Structure> optionalStructure = getStructureOfMethod(method);
        optionalStructure.ifPresent(structure -> {
            SpecificationStructureValidator validator = new SpecificationStructureValidator(structure);
            validator.test();
        });
    }

    private Optional<Structure> getStructureOfMethod(Method callerMethod) {
        Class<?> clazz = callerMethod.getDeclaringClass();
        List<MethodInstruction> instructions = methodInstructionsProvider.readInstructions(clazz, callerMethod);
        return Optional.of(mapToStructureBlocks(callerMethod, instructions));
    }

    private Structure mapToStructureBlocks(Method method, List<MethodInstruction> instructions) {
        LinkedList<Block> blocks = instructions.stream()
                .map(Block::matchWithInstruction)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toCollection(LinkedList::new));
        return new Structure(method, blocks);
    }
}
