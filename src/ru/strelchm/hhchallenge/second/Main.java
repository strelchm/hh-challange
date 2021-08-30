package ru.strelchm.hhchallenge.second;

import java.util.*;

public class Main {
    private static final int MAX_LINE_LENGTH = 110;

    private static final char MULTIPLY_SIGN = '*'; // умножение
    private static final char PLUS_SIGN = '+'; // сложение
    private static final char MINUS_SIGN = '-'; // вычитание
    private static final char GRATER_SIGN = '>'; // левый операнд больше, чем правый. 1, если истинно, 0 - если ложно
    private static final char OPEN_BRACKET_SIGN = '(';
    private static final char CLOSE_BRACKET_SIGN = ')';
    public static final char SPACE_SIGN = ' ';

    private static String line;
    private static int lineSize;
    private static int ch;
    private static int currentPosition = -1;

    private static final List<CubeValueNode> cubeValueNodes = new ArrayList<>();
    private static final Map<Integer, Integer> probabilities = new HashMap<>();
    private static double nodeProbability = -1;


    public static void main(String[] args) {
        line = new Scanner(System.in).nextLine();
        if (line.length() > MAX_LINE_LENGTH) {
            System.err.println("Line is grater than " + MAX_LINE_LENGTH);
        }
        lineSize = line.length();

        ExpressionNode node = getRootExpressionNode();

        do {
            if (nodeProbability == -1) {
                nodeProbability = node.getProbability();
            }
            addToProbabilities(node.calculate());
        } while (!cubeValueNodes.isEmpty() && turnCubeValueNodes(0));
        probabilities.entrySet().stream().sorted(Comparator.comparingLong(Map.Entry::getKey))
                .forEach(v -> System.out.println(v.getKey() + String.format(Locale.US, " %.2f", v.getValue() * nodeProbability * 100)));
    }

    private static boolean turnCubeValueNodes(int arrIndex) {
        CubeValueNode currentValueNode = cubeValueNodes.get(arrIndex);
        if (currentValueNode.value < currentValueNode.valuesCount) {
            currentValueNode.value++;
            return true;
        }
        currentValueNode.value = 1;
        return arrIndex + 1 != cubeValueNodes.size() && turnCubeValueNodes(arrIndex + 1);
    }

    private static void addToProbabilities(int value) {
        probabilities.put(value, probabilities.containsKey(value) ? probabilities.get(value) + 1 : 1);
    }

    private static ExpressionNode getRootExpressionNode() {
        nextChar();
        ExpressionNode x = parseAllPrecedenceLevels();
        if (currentPosition < lineSize) {
            throw new UnsupportedOperationException("Unexpected: " + (char) ch);
        }
        return x;
    }

    private static ExpressionNode parseAllPrecedenceLevels() {
        ExpressionNode x = parsePlusMinusLevel();
        while (true) {
            if (!eat(GRATER_SIGN)) {
                return x;
            }
            x = new GreaterNode(x, parsePlusMinusLevel());
        }
    }

    private static ExpressionNode parsePlusMinusLevel() {
        ExpressionNode x = parseMultiplyDivideLevel();
        while (true) {
            if (eat(PLUS_SIGN)) {
                x = new PlusNode(x, parseMultiplyDivideLevel()); // addition
            } else if (eat(MINUS_SIGN)) {
                x = new MinusNode(x, parseMultiplyDivideLevel()); // subtraction
            } else return x;
        }
    }

    private static ExpressionNode parseMultiplyDivideLevel() {
        ExpressionNode x = parseFactor();
        while (true) {
            if (eat(MULTIPLY_SIGN)) {
                x = new MultiplyNode(x, parseFactor()); // multiplication
            }
//        else if (eat('/')) {
//            return new DivideNode(x, parseFactor()); // division
//        }
            else return x;
        }
    }

    /**
     * Парсинг множителя
     *
     * @return
     */
    private static ExpressionNode parseFactor() {
        int startPos = currentPosition;
        if (eat(OPEN_BRACKET_SIGN)) { // скобки
            ExpressionNode node = parseAllPrecedenceLevels();
            eat(CLOSE_BRACKET_SIGN);
            return node;
        } else if ((ch >= '0' && ch <= '9')) { // числа
            while ((ch >= '0' && ch <= '9')) {
                nextChar();
            }
            return new ValueNode(Integer.parseInt(line.substring(startPos, currentPosition)));
        } else if (ch == 'd') {
            nextChar();
            startPos++;
            while ((ch >= '0' && ch <= '9')) {
                nextChar();
            }
            return new CubeValueNode(Integer.parseInt(line.substring(startPos, currentPosition)));
        } else {
            throw new UnsupportedOperationException("Unexpected: " + (char) ch);
        }
    }

    /**
     * Процессинг необходимого символа
     *
     * @param charToEat
     * @return
     */
    private static boolean eat(int charToEat) {
        while (ch == SPACE_SIGN) {
            nextChar();
        }
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

    //=====================================================================//
    //=============================== NODES ===============================//
    //=====================================================================//
    private interface ExpressionNode {
        int calculate();

        double getProbability();
    }

    private abstract static class TwoLeavesNode implements ExpressionNode {
        protected ExpressionNode left;
        protected ExpressionNode right;

        TwoLeavesNode(ExpressionNode left, ExpressionNode right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public double getProbability() {
            return left.getProbability() * right.getProbability();
        }

        @Override
        public String toString() {
            return this.getClass().getName() + "{" +
                    "left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    private static class GreaterNode extends TwoLeavesNode {
        GreaterNode(ExpressionNode left, ExpressionNode right) {
            super(left, right);
        }

        @Override
        public int calculate() {
            return left.calculate() > right.calculate() ? 1 : 0;
        }
    }

    private static class PlusNode extends TwoLeavesNode {
        PlusNode(ExpressionNode left, ExpressionNode right) {
            super(left, right);
        }

        @Override
        public int calculate() {
            return left.calculate() + right.calculate();
        }
    }

    private static class MinusNode extends TwoLeavesNode {
        MinusNode(ExpressionNode left, ExpressionNode right) {
            super(left, right);
        }

        @Override
        public int calculate() {
            return left.calculate() - right.calculate();
        }
    }

    private static class ValueNode implements ExpressionNode {
        protected int value;

        ValueNode(int value) {
            this.value = value;
        }

        @Override
        public int calculate() {
            return value;
        }

        @Override
        public double getProbability() {
            return 1.0;
        }

        @Override
        public String toString() {
            return "ValueNode{" +
                    "value=" + value +
                    '}';
        }
    }

    private static class CubeValueNode implements ExpressionNode {
        private int value;
        private final int valuesCount;

        CubeValueNode(int valuesCount) {
            this.valuesCount = valuesCount;
            this.value = 1;
            cubeValueNodes.add(this);
        }

        @Override
        public int calculate() {
            return value;
        }

        @Override
        public double getProbability() {
            return 1f / valuesCount;
        }
    }

    private static class MultiplyNode extends TwoLeavesNode {
        MultiplyNode(ExpressionNode left, ExpressionNode right) {
            super(left, right);
        }

        @Override
        public int calculate() {
            return left.calculate() * right.calculate();
        }
    }

    @Deprecated
    private static class DivideNode extends TwoLeavesNode {
        DivideNode(ExpressionNode left, ExpressionNode right) {
            super(left, right);
        }

        @Override
        public int calculate() {
            return left.calculate() / right.calculate();
        }
    }
}
