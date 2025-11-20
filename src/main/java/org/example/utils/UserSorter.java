package org.example.utils;

import org.example.entity.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class UserSorter {
    private UserSorter(){}

    /**
     * Sorts {@link User}'s by {@link User#name}.
     * @param users The {@link List} to be sorted.
     * @return The sorted {@link List} of {@link User}'s.
     */
    public static List<User> sortByName(List<User> users) {
        //users.sort(Comparator.comparing(User::getName).reversed());
        users.sort((u1, u2) -> u1.getName().compareTo(u2.getName()));
        return users;
    }

    /**
     * Sorts {@link User}'s by {@link User#age}.
     * @param users The {@link List} to be sorted.
     * @return The sorted {@link List} of {@link User}'s.
     */
    public static List<User> sortByAge(List<User> users) {
        users.sort((u1, u2) -> u1.getAge().compareTo(u2.getAge()));
        //users.sort(Comparator.comparing(User::getAge));
        //users = users.reversed();
        return users;
    }

    public static <T extends User, U extends Comparable<? super U>> Optional<User> min(List<User> users, Function<? super T, ? extends U> f) {
        return users.stream().min((Comparator<? super User>) Comparator.comparing(f));
    }
}
