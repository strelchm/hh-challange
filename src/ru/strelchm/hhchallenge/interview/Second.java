package ru.strelchm.hhchallenge.interview;

import java.util.ArrayList;
import java.util.List;

/**
 * Дан несортированный массив из целых чисел **A**.
 * <p>
 * Дано целое число **X**.
 * <p>
 * Нужно найти, есть ли в неотсортированном массиве два элемента с ***разными значениями*** таких, что **A₁** + **A₂** = **X**
 * <p>
 * Для решения нет ограничений по памяти, но следует оптимизировать решение для максимального быстродействия.
 */
public class Second {
    private final List<Integer> list;

    public Second(int... vals) {
        list = new ArrayList<>();
        for (int a : vals) {
            list.add(a);
        }
    }

    private boolean containsXSum(int x) {
        List<Integer> buffer = new ArrayList<>();
        for (Integer v : list) {
            boolean inBuff = false;
            for (Integer buffVal : buffer) {
                if (v.equals(buffVal)) {
                    inBuff = true;
                } else if (v + buffVal == x) {
                    return true;
                }
            }
            if (!inBuff) {
                buffer.add(v);
            }
        }
        return false;
    }

//    @Override
//    public String toString() {
//        return "Second{" +
//                Arrays.toString(list.toArray()) +
//                '}';
//    }

    public static void main(String[] args) {
        Second second = new Second(3, 4, 5, 6, 7);
        System.out.println(second.containsXSum(7));
        System.out.println(second.containsXSum(10));
        System.out.println(second.containsXSum(12));
        System.out.println(second.containsXSum(100));
    }
}
