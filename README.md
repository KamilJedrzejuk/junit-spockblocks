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
public class BlockTest extends Specification {

    
    @Test
    public void should_get_proper_blocks_from_method() {

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
 
 
 
