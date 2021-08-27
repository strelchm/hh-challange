package ru.strelchm.hhchallenge;

import java.util.Scanner;

public class Main {
    private static final int MAX_LINE_LENGTH = 110;

    private static class Node {
        private Node childNode;
        private NodeType type;
    }

    private static enum NodeType {
        BRACKET(3),
        MULTIPLY(2),
        PLUS(1),
        MINUS(1),
        GRATER(0),
        NUMBER(-1),
        CUBE_NUMBER(-1);

        private NodeType(int priority) {
            this.priority = priority;
        }

        private int priority;
    }

    private static final char STAR_SIGN = '*'; // умножение
    private static final char PLUS_SIGN = '+'; // сложение
    private static final char MINUS_SIGN = '-'; // вычитание
    private static final char GRATER_SIGN = '>'; // левый операнд больше, чем правый. 1, если истинно, 0 - если ложно
    private static final char OPEN_BRACKET_SIGN = '(';
    private static final char CLOSE_BRACKET_SIGN = ')';

    private static String line;
    private static int lineSize;
    private static int ch;
    private static int currentPosition = -1;
    private static int currentValue = 0;

    public static void main(String[] args) {
        line = new Scanner(System.in).nextLine();
        if (line.length() > MAX_LINE_LENGTH) {
            System.err.println("Line is grater than " + MAX_LINE_LENGTH);
        }
        lineSize = line.length();
        System.out.println("Line is " + line);

        System.out.println(parse());

    }

//    private static void processLine() {
//
//    }

//    private static void processSign(char currentChar) {
//        switch (currentChar) {
//            case Sign.OPEN_BRACKET.value:
//                // process brackets
//                continue;
//            case STAR_SIGN:
//
//            case PLUS_SIGN:
//            case MINUS_SIGN:
//            case GRATER_SIGN:
//                break;
//            default:
//
//                break;
//        }
//    }

    private static double parse() {
        nextChar();
        double x = parseAllPrecedenceLevels();
        if (currentPosition < lineSize) throw new RuntimeException("Unexpected: " + (char) ch);
        return x;
    }

    private static double parseAllPrecedenceLevels() {
        double x = parsePlusMinusLevel();
        if (eat('>')) {
            return x > parsePlusMinusLevel() ? 1 : 0;
        } else {
            return x;
        }
    }

    private static double parsePlusMinusLevel() {
        double x = parseMultiplyDivideLevel();
        for (; ; ) {
            if (eat('+')) x += parseMultiplyDivideLevel(); // addition
            else if (eat('-')) x -= parseMultiplyDivideLevel(); // subtraction
            else return x;
        }
    }

    private static double parseMultiplyDivideLevel() {
        double x = parseFactor();
        for (; ; ) {
            if (eat('*')) x *= parseFactor(); // multiplication
            else if (eat('/')) x /= parseFactor(); // division
            else return x;
        }
    }

    /**
     * Парсинг множителя
     *
     * @return
     */
    private static double parseFactor() {
        if (eat('+')) {
            return parseFactor(); // unary plus
        }
        if (eat('-')) {
            return -parseFactor(); // unary minus
        }

        double x;
        int startPos = currentPosition;
        if (eat('(')) { // parentheses
            x = parseAllPrecedenceLevels();
            eat(')');
        } else if ((ch >= '0' && ch <= '9')) { // numbers
            while ((ch >= '0' && ch <= '9')) nextChar();
            x = Double.parseDouble(line.substring(startPos, currentPosition));
        } else if (ch == '>') { // functions
            while (ch >= 'a' && ch <= 'z') nextChar();
            String func = line.substring(startPos, currentPosition);
            x = parseFactor();
            if (func.equals("sqrt")) x = Math.sqrt(x);
            else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
            else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
            else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
            else throw new RuntimeException("Unknown function: " + func);
        } else {
            throw new RuntimeException("Unexpected: " + (char) ch);
        }

        if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

        return x;
    }

    /**
     * Процессинг необходимого символа
     *
     * @param charToEat
     * @return
     */
    private static boolean eat(int charToEat) {
        while (ch == ' ') nextChar();
        if (ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    /**
     * Перешагивание на следующий символ
     */
    private static void nextChar() {
        ch = (++currentPosition < lineSize) ? line.charAt(currentPosition) : -1;
    }
}
