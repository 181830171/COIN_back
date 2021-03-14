package com.example.neo4jKG.Util;

import org.springframework.stereotype.Component;

@Component
public class MathUtil {
    public double remainTwoFractions(double d){
        return ((int)(d * 100))/100.0;
    }
}
