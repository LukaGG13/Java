package org.example.entity;

/**
 * Abstract class representing the user.
 * @version 1.0
 * @author luka
 */
public abstract class User {
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
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}