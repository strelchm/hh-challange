package ru.strelchm.hhchallenge.interview;

import java.util.HashMap;
import java.util.Map;


/**
 * Дан несортированный массив из целых чисел **A**.

 Дано целое число **X**.

 Нужно найти, есть ли в неотсортированном массиве два элемента с ***разными значениями*** таких, что **A₁** + **A₂** = **X**

 Для решения нет ограничений по памяти, но следует оптимизировать решение для максимального быстродействия.
 */

public class SecondOptimizedWithDuplicates {
    private final Map<Integer, Integer> map = new HashMap<>();

    public SecondOptimizedWithDuplicates(int... vals) {
        for(int a : vals) {
            map.put(a, !map.containsKey(a) ? 1 : map.get(a) + 1);
        }
    }

    public boolean containsXSum(int x) {

        for (Map.Entry<Integer, Integer> v :  map.entrySet()) {
            int difference = x - v.getKey();
            if ((v.getValue() > 1 && difference == v.getKey()) || map.containsKey(difference)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SecondOptimizedWithDuplicates second = new SecondOptimizedWithDuplicates(3, 4,5,6,7);
        System.out.println(second.containsXSum(7));
        System.out.println(second.containsXSum(10));
        System.out.println(second.containsXSum(12));
        System.out.println(second.containsXSum(100));
    }
}