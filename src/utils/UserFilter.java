package utils;

import entity.User;

import java.util.Arrays;

public class UserFilter {

    private User[] addUserToResult(User[] result, Integer n, User userToBeAdded) {
        User[] temp = Arrays.copyOf(result, n);
        temp[n - 1] = userToBeAdded;
        return temp;
    }

    public User[] filterUserByAge(User[] users, Integer targetAge) {
        User[] result = new User[0];
        for (Integer i = 0; i < users.length; i++) {
            if (users[i].getAge().equals(targetAge)) {
                result = addUserToResult(result, result.length + 1, users[i]);
            }
        }
        return result;
    }

    public User[] filterUserByName(User[] users, String targetName) {
        User[] result = new User[0];
        for (Integer i = 0; i < users.length; i++) {
            if (users[i].getName().equals(targetName)) {
                result = addUserToResult(result, result.length + 1, users[i]);
            }
        }
        return result;
    }

    public User findMinAge(User[] users) {
        User result = users[0];
        for (Integer i = 1; i < users.length; i++) {
            if (users[i].getAge() < result.getAge()) {
                result = users[i];
            }
        }
        return result;
    }

    public User findMaxAge(User[] users) {
        User result = users[0];
        for (Integer i = 1; i < users.length; i++) {
            if (users[i].getAge() > result.getAge()) {
                result = users[i];
            }
        }
        return result;
    }

    public User findMinName(User[] users) {
        User result = users[0];
        for (Integer i = 1; i < users.length; i++) {
            if (users[i].getName().compareTo(result.getName()) < 0) {
                result = users[i];
            }
        }
        return result;
    }

    public User findMaxName(User[] users) {
        User result = users[0];
        for (Integer i = 1; i < users.length; i++) {
            if (users[i].getName().compareTo(result.getName()) > 0) {
                result = users[i];
            }
        }
        return result;
    }
}

