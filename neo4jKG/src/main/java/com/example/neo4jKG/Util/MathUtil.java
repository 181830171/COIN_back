package com.example.neo4jKG.Util;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class MathUtil {
    public double remainTwoFractions(double d){
        return Double.parseDouble(String.format("%.2f",d));
    }
}
