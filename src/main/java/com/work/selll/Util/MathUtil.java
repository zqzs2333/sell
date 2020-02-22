package com.work.selll.Util;

public class MathUtil {
    private static  final Double Money_Range =0.01;
    public static Boolean equals(Double d1,Double d2)
    {
        double abs = Math.abs(d1 - d2);
        if(abs < Money_Range)
        {
            return true;
        }
        else {
            return false;
        }
    }
}
