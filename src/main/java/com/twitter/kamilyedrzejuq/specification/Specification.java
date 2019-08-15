package com.twitter.kamilyedrzejuq.specification;

import com.twitter.kamilyedrzejuq.junit.SpockBlockExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({SpockBlockExtension.class})
public class Specification {

    static protected void GIVEN(){}
    static protected void GIVEN(final String dec){}

    static protected void WHEN(){}
    static protected void WHEN(final String desc){}

    static protected void THEN(){}
    static protected void THEN(final String desc){}

    static protected void EXPECT(){}
    static protected void EXPECT(final String desc){}

    static protected void AND(){}
    static protected void AND(final String desc){}
}
