package ru.strelchm.hhchallenge.first;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Ограничение времени, с	1
 * Ограничение памяти, МБ	64
 * Общее число попыток отправки	15
 * <p>
 * Петр Васильевич, директор ОАО "Рога и рога", собирается раздать премию всем менеджерам компании, он добрый и честный человек,
 * поэтому хочет, чтобы премия была равной для всех, целой, и максимально возможной, а также, чтобы ее можно было отправить,
 * совершив ровно по одной транзакции для каждого менеджера.
 * <p>
 * У Петра Васильевича открыто N корпоративных счетов, на которых лежат разные суммы денег, а в компании работает M менеджеров.
 * Необходимо выяснить максимальный размер премии, которую можно отправить с учетом условий. Если денег на счетах компании не
 * хватит на то, чтобы выдать премию хотя бы по 1 у.е. - значит премии не будет, и нужно вывести 0.
 * <p>
 * <p>
 * Входные данные (поступают в стандартный поток ввода)
 * Первая строка - целые числа N и M через пробел (1≤N≤100 000, 1≤M≤100 000)
 * <p>
 * Далее N строк, на каждой из которых одно целое число Cn (0≤Cn≤100 000 000)
 * <p>
 * <p>
 * Выходные данные (ожидаются в стандартном потоке вывода)
 * Одно целое число, максимально возможная премия
 * <p>
 * <p>
 * Пример 1
 * Ввод:
 * <p>
 * 3 6
 * 453
 * 220
 * 601
 * Вывод:
 * <p>
 * 200
 * <p>
 * Пример 2
 * Ввод:
 * <p>
 * 2 100
 * 99
 * 1
 * Вывод:
 * <p>
 * 1
 * <p>
 * Пример 3
 * Ввод:
 * <p>
 * 2 100
 * 98
 * 1
 * Вывод:
 * <p>
 * 0
 */
public class Main {
    private static int managerCount;
    private static int[] accountMoney;
    private static int sum = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
        String firstLine = bi.readLine();

        String[] firstLineArray = firstLine.split(" ");

        int accountCount = Integer.parseInt(firstLineArray[0]);
        accountMoney = new int[accountCount];
        managerCount = Integer.parseInt(firstLineArray[1]);

        for (int i = 0; i < accountCount; i++) {
            accountMoney[i] = Integer.parseInt(bi.readLine());
            sum += accountMoney[i];
        }
        System.out.println(getValue());
    }

    private static boolean isNear(int celery, int managerCnt) {
        for (Integer v : accountMoney) {
            int totalSum = v;
            if (totalSum >= celery && managerCnt > 0) {
                managerCnt -= totalSum / celery;
            }
        }
        return managerCnt <= 0;
    }

    private static int getValue() {
        int highLimit = sum / managerCount;
        int lowLimit = 0;
        int halfPart;
        while (highLimit > lowLimit) {
            halfPart = lowLimit + (highLimit - lowLimit + 1) / 2;
            if (isNear(halfPart, managerCount)) {
                lowLimit = halfPart;
            } else {
                highLimit = halfPart - 1;
            }
        }
        return lowLimit;
    }
}
