package org.example.utils;

import org.example.entity.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public final class UserSorter {
    private UserSorter(){}

    public static List<User> sortNew(List<User> users) {
        Comparator<User> comparator = (o1, o2) -> {
            if(o2.getAge().compareTo(o1.getAge()) != 0)
                return o2.getAge().compareTo(o1.getAge());
            else
                return o1.getName().compareTo(o2.getName());
        };

        users.sort(comparator);
        return users;
    }
    public static List<User> sortOld(List<User> users) {
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if(o2.getAge().compareTo(o1.getAge()) != 0) {
                    return o2.getAge().compareTo(o1.getAge());
                }
                else {
                    return o1.getName().compareTo(o2.getName());
                }
            }
        });
        return users;
    }

    public static List<User> sort(List<User> users) {
        users.sort((u1, u2) -> {
            if(u2.getAge().compareTo(u1.getAge()) != 0)
                return u2.getAge().compareTo(u1.getAge());
            else
                return u1.getName().compareTo(u2.getName());
        });
        return users;
    }

    public static List<User> sortByName(List<User> users) {
        //users.sort((u1, u2) -> u2.getName().compareTo(u1.getName()));
        users.sort(Comparator.comparing(User::getName).reversed()); //TODO FTW java zasto bi mi to radila
        return users;
    }

    public static List<User> sortByAge(List<User> users) {
        users.sort(Comparator.comparing(User::getAge));
        users = users.reversed();
        return users;
    }
}
