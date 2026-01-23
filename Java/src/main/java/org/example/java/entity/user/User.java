package org.example.java.entity.user;

import javafx.util.Pair;
import org.example.java.entity.interfaces.Searchable;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Abstract class representing the user.
 * @version 1.0
 * @author luka
 */
public abstract class User implements Searchable {
    private final String name;
    private final Integer age;
    private final UUID id;

    /**
     * Constructor for the user class with auto generating id.
     * @param name name of the user
     * @param age age of the user
     */
    protected User(String name, Integer age) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.age = age;
    }

    /**
     * Constructor for the user class.
     * @param id {@link UUID} of the user
     * @param name name of the user
     * @param age age of the user
     */
    protected User(UUID id, String name, Integer age) {
        this.id = id;
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

    /**
     * Gets the uuid of the user.
     * @return returns the uuid.
     */
    public UUID getId() {
        return id;
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
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public Set<Pair<String, String>> getKeyWord() {
        return Set.of(new Pair<String, String>("Class", "User"), new Pair<String, String>("Name", getName()), new Pair<String, String>("Age", getAge().toString()));
    }
}