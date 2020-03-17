# Table of contents

1. [About](#about)
2. [Stack technology](#Stack-technology)

## About
Junit-spockblocks is simple junit extension which allow us to use a some subset of blocks known
from a Spock framework:
 - given
 - and
 - when
 - then
 - expect
 
## Example

```ruby
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

## Stack technology
 - junit 5
 - junit platform
 - asm library (bytecode manipulation and analysis framework)
 
 
 ## TODO
  - how to check proper combination of labels
  - how to check if in the THEN section we have assertion, and also in the AND sections which can occur after THEN section
