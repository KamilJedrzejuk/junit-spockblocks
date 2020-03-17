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

## Stack technology
 - junit 5
 - junit platform
 - asm library (bytecode manipulation and analysis framework)
 
 
 ## TODO
  - how to check proper combination of labels
  - how to check if in the THEN section we have assertion, and also in the AND sections which can occur after THEN section
