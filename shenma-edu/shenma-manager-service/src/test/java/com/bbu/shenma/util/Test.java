package com.bbu.shenma.util;

import org.assertj.core.util.Arrays;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        String [] X = {"a","c","e"};
        String [] Y = {"b","c"};
        List<Object> obj = Arrays.asList(X);
        for(int i = 0; i < X.length; i++){
            for(int j = 0; j < Y.length; j++){
                if (X[i].equals(Y[j])) {
                    obj.remove(X[i]);
                }
            }
        }
        List<Object> obj2 = Arrays.asList(Y);
        obj.addAll(obj2);
        System.out.println(obj);
    }
}
