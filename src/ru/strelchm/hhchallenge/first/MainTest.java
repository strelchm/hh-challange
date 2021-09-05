package ru.strelchm.hhchallenge.first;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    public static final int MAX_ACCOUNT_MONEY = 100_000_000;
    public static final int MAX_ACCOUNTS_COUNT = 100_000;
    private Main main;
    private static int[] arr;

    @BeforeEach
    void setUp() {
        main = new Main();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getValue() {
        for (int n = 1; n <= MAX_ACCOUNTS_COUNT; n++) {
            arr = new int[n];
            for(int i = 0; i < n; i++) {
                arr[i] = 100_000_000;
            }
            do {
                for (int m = MAX_ACCOUNTS_COUNT; m <= MAX_ACCOUNTS_COUNT; m++) {
                    compareResults(m, arr);
                }
            } while (turnArrayValues(0));
        }
    }

    private void compareResults(int m, int[] values) {
        assertEquals(main.getValue(getSum(values), values, m), getCorrectValue(values, m));
    }

    private long getSum(int[] arr) {
        long sum = 0;
        for(int val : arr) {
            sum += val;
        }
        return sum;
    }

    private long getCorrectValue(int[] arr, int managerCount) {
        long highLimit = getSum(arr) / managerCount;
        long halfPart = highLimit;
        while (halfPart != 0 && !(main.isNear((int)halfPart, arr, managerCount) && !main.isNear((int)(halfPart + 1), arr, managerCount))) {
            --halfPart;
        }
        return halfPart;
    }

    private static boolean turnArrayValues(int arrIndex) {
        int currentValue = arr[arrIndex];
        if (currentValue < MAX_ACCOUNT_MONEY) {
            arr[arrIndex]++;
            return true;
        }
        arr[arrIndex] = 0;
        return arrIndex + 1 != arr.length && turnArrayValues(arrIndex + 1);
    }
}