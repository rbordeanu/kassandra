package com.kassandra.compiler.java.utils;

public class Fibonacci {

    public static double getNumber(int n) {

        if(n==0) {
            return 0;
        }

        if(n==1) {
            return 1;
        }

        return getNumber(n-1) + getNumber(n-2);
    }

    public static void main(String[] args) {

        System.out.println(getNumber(3));
    }
}
