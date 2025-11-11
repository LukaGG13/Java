package org.example.utils;

import org.example.entity.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public final class UserSorter {
    private UserSorter(){}

    /**
     * Sorts {@link User}'s by {@link User#name}.
     * @param users The {@link List} to be sorted.
     * @return The sorted {@link List} of {@link User}'s.
     */
    public static List<User> sortByName(List<User> users) {
        users.sort(Comparator.comparing(User::getName).reversed());
        return users;
    }

    /**
     * Sorts {@link User}'s by {@link User#age}.
     * @param users The {@link List} to be sorted.
     * @return The sorted {@link List} of {@link User}'s.
     */
    public static List<User> sortByAge(List<User> users) {
        users.sort(Comparator.comparing(User::getAge));
        users = users.reversed();
        return users;
    }
}
