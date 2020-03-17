# Table of contents

1. [About](#about)
2. [Example](#Example)
3. [Implementation](#Implementation)
4. [Stack technology](#Stack-technology)


## About
Junit-spockblocks is simple junit extension which allow us to use a some subset of blocks known
from a Spock framework:
 - given
 - and
 - when
 - then
 - expect
 
## Example

```java
public class BlaBlaSpec extends Specification {

    
    @Test
    public void should_bla_bla_bla() {

        GIVEN();
        //code omitted

        WHEN();
        //code omitted

        THEN();
        //code omitted
        
    }
}

``` 
## Implementation

Verifier class is a Junit extension, which check a test method.

```java
public class Verifier implements AfterEachCallback {

    static private final SpecificationVerifier verifier = new SpecificationVerifier();

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        context.getTestMethod().ifPresent(method -> {
            verifier.verifyMethod(method);
        });
    }
}
```

Every test extends Specification class like in Spock library:

```java
@ExtendWith(Verifier.class)
public class Specification {

    final protected void GIVEN() {
    }

    final protected void GIVEN(final String dec) {
    }

    final protected void WHEN() {
    }

    final protected void WHEN(final String desc) {
    }

    final protected void THEN() {
    }

    final protected void THEN(final String desc) {
    }

    final protected void EXPECT() {
    }

    final protected void EXPECT(final String desc) {
    }

    final protected void AND() {
    }

    final protected void AND(final String desc) {
    }

}
```

Class belowe validate a structure of test junit method:

```java
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
```

To get a information about labels (defacto java method from Specification class), I'm using a [asm library](https://asm.ow2.io/) (Java bytecode manipulation and analysis framework).


```java
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MethodInstructionsProvider {

    private final ClassNode classNode = new ClassNode();
    private final Printer printer = new Textifier();
    private final TraceMethodVisitor mp = new TraceMethodVisitor(printer);

    public List<MethodInstruction> readInstructions(Class<?> clazz, Method method) {
        ClassReader classReader = createClassReader(clazz);
        classReader.accept(classNode, 0);

        Optional<MethodNode> methodNodeOptional = getMethodNode(method);
        LinkedList<MethodInstruction> instructions = methodNodeOptional.map(this::getMethodInstructions)
                .orElse(new LinkedList<>());

        return instructions;
    }

    private Optional<MethodNode> getMethodNode(Method method) {
        return classNode.methods.stream()
                .filter(methodNode -> methodNode.name.equalsIgnoreCase(method.getName()))
                .findFirst();
    }

    private LinkedList<MethodInstruction> getMethodInstructions(MethodNode methodNode) {
        LinkedList<MethodInstruction> instructions = new LinkedList<>();
        InsnList inList = methodNode.instructions;
        for (int i = 0; i < inList.size(); i++) {
            inList.get(i).accept(mp);
            StringWriter sw = new StringWriter();
            printer.print(new PrintWriter(sw));
            printer.getText().clear();

            String lineCode = sw.toString();
            instructions.add(new MethodInstruction(lineCode));
        }
        return instructions;
    }

    private ClassReader createClassReader(Class<?> clazz) {
        try {
            return new ClassReader(clazz.getName());
        } catch (IOException e) {
            throw new ReadClassException(e.getMessage(), e);
        }
    }
}
```

## Stack technology
 - junit 5
 - junit platform
 - asm library (bytecode manipulation and analysis framework)
 
 
 ## TODO
  - how to check proper combination of labels
  - how to check if in the THEN section we have assertion, and also in the AND sections which can occur after THEN section
