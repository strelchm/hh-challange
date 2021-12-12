package ru.strelchm.hhchallenge.interview;

import java.util.*;

/**
 * **Реализовать структуру RandomSet, поддерживающую следующие методы:**
 *
 * ***add(element)*** - добавление элемента
 *
 * ***remove(element)*** - удаление элемента по значению
 *
 * ***get_random()*** - получение любого случайного значения из RandomSet
 *
 * Где *element* - значение элемента.
 *
 * Все операции должны выполняться за константное время (O(1)).
 *
 * Новая структура должна поддерживать свойства множества.
 *
 * Можно использовать стандартную функцию получения случайного значения и любые стандартные структуры данных.
 */
public class First {
    interface IRandomSet<T> {
        void add(T element);
        void remove(T element);
        T getRandom();
    }

    static class RandomSet<T> implements IRandomSet<T> {
        private final Map<T, Integer> map = new HashMap<>();
        private final List<T> list = new ArrayList<>();

        @Override
        public void add(T element) {
            int currentSize = list.size();
            list.add(element);
            map.put(element, currentSize);
        }

        @Override
        public void remove(T element) {
            Integer arrIndex = map.get(element);
            if (arrIndex == null) {
                return;
            }
            list.set(arrIndex, list.get(list.size() - 1));
            list.remove(list.size() - 1);
            map.remove(element);
        }

        @Override
        public T getRandom() {
            if (list.isEmpty()) {
                return null;
            }
            int randomValue = (int) (Math.random() * (list.size() - 1));
            return list.get(randomValue);
        }

        @Override
        public String toString() {
            return "RandomSet{" +
                    Arrays.toString(list.toArray()) +
                    '}';
        }
    }

    public static void main(String[] args) {
        IRandomSet<String> set = new RandomSet<>();
        set.add("Hello");
        set.add("World");
        set.add("Tom");
        set.add("Jerry");

        System.out.println(set);

        set.remove("World");
        System.out.println(set);

        System.out.println(set.getRandom());
    }
}
