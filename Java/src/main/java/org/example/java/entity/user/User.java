package org.example.java.entity.user;

import javafx.util.Pair;
import org.example.java.entity.interfaces.Searchable;

import java.util.Objects;
import java.util.Set;

/**
 * Abstract class representing the user.
 * @version 1.0
 * @author luka
 */
public abstract class User implements Searchable {
    private final String name;
    private final Integer age;

    /**
     * Constructor for the user class.
     * @param name name of the user
     * @param age age of the user
     */
    protected User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Gets the name of the user.
     * @return returns the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the age of the user.
     * @return returns the age
     */
    public Integer getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public Set<Pair<String, String>> getKeyWord() {
        return Set.of(new Pair<String, String>("Class", "User"), new Pair<String, String>("Name", getName()), new Pair<String, String>("Age", getAge().toString()));
    }
}