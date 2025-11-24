package org.example.utils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Test {
    record Car(String Model, BigDecimal Price){};
    static List<String> l1;
    static List<Integer> l2 = List.of(0,1,1,1,1,1,2,3,4,5,6,6,6,6);

    static <T> List<T> removeNulls(List <T> l) {
        Predicate<T> p = (T t ) -> t != null;
        l = l.stream().filter(p).toList();
        return l;
    }

    static void addStrings(List<String> l) {
        l1.add("Luka");
        l1.add("Ivan");
        l1.add("testic");
        l1.add(null);
        l1.add("Erik");
        l1.add("Rubi");
        l1.add("test");
        l1.add("Bumbi");
        l1.add(null);
    }

    static <T> void printList(List<T> l ) {
        for (var e : l) System.out.println(e);
        System.out.println();
    }

    static List<String > odgovorA(List<String> l) {
        return removeNulls(l);
    }

    static List<String> odgovorB(List<String> l) {
        Predicate<String> p = (String s) -> s.contains("test");
        return l.stream().filter(p).toList();
    }

    static List<Integer> odgovorC(List<Integer> l) {
        //Predicate<Integer> p = x -> x + 1; TODO compile error int cannot be converted to boolean
        return l;
    }

    static List<String> odgovorD(List<String> l) {
        //Predicate<String> p = () -> System.out.println("hello world"); TODO compile error void cannot be converted to boolean
        return l;
    }

    static <T> List<T> odgovorE(List<T> l) {
        BiPredicate<T,T> p = (T x, T y) -> x == y;
        return l;
        //return l.stream().filter();
    }

    static <T> List<T> odgovorF(List<T> l) {
        Predicate<List<String>> p = (List<String> list) -> list.isEmpty();
        return l;
    }
    //A,B,C,D,E,F,G,H,J,K,L,M,N,O,P

    public static class CarComparator implements Comparator<Car> {
        @Override
        public int compare(Car c1, Car c2) {
            if(c1.Price().compareTo(c2.Price()) != 0) {
                return c1.Model().compareTo(c2.Model());
            }
            else {
                return c2.Price().compareTo(c1.Price());
            }
        }
    }

    static void main() {
        /*
        Car first = new Car("Tesla", new BigDecimal(50000));
        Car second = new Car("VW Golf", new BigDecimal(20000));
        Car third = new Car("Hyundai", new BigDecimal(30000));
        Car fourth = new Car("Mercedes", new BigDecimal(40000));
        List<Car> cars = Arrays.asList(first, second, third, fourth);
        Collections.sort(cars, new CarComparator());
        for(Car car : cars) {
            System.out.println(car.Model());
        }
        System.out.println(cars.stream()
                .sorted(new CarComparator())
                .map(Car::Price)
                .findFirst());
        String naziv = "Kontrolna zadaća";
        String naziv2 = new String("Kontrolna zadaća");
        System.out.println(naziv);
        System.out.println(naziv2);
         */
        List<String> l = List.of("a", "b", "c");
        String s = l.stream().reduce(null, (x, y) -> x + y);
        System.out.println(s);
    }
}


// gg
// Hyundi
// mer]a
// tesla
// golf