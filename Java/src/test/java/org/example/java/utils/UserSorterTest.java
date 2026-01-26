package org.example.java.utils;

import org.example.java.entity.admin.Admin;
import org.example.java.entity.guest.Guest;
import org.example.java.entity.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserSorter Tests")
class UserSorterTest {

    private List<User> users;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
    }

    @Nested
    @DisplayName("Sort By Name Tests")
    class SortByNameTest {

        @Test
        @DisplayName("Should sort users alphabetically by name")
        void testSortByNameAlphabetically() {
            users.add(new Guest("Zoe", 25));
            users.add(new Guest("Alice", 30));
            users.add(new Guest("Bob", 28));

            List<User> sorted = UserSorter.sortByName(users);

            assertEquals("Alice", sorted.get(0).getName());
            assertEquals("Bob", sorted.get(1).getName());
            assertEquals("Zoe", sorted.get(2).getName());
        }

        @Test
        @DisplayName("Should handle empty list")
        void testSortByNameEmptyList() {
            List<User> sorted = UserSorter.sortByName(users);
            assertTrue(sorted.isEmpty());
        }

        @Test
        @DisplayName("Should handle single user")
        void testSortByNameSingleUser() {
            users.add(new Guest("Alice", 25));
            List<User> sorted = UserSorter.sortByName(users);

            assertEquals(1, sorted.size());
            assertEquals("Alice", sorted.get(0).getName());
        }

        @Test
        @DisplayName("Should preserve order of users with same name")
        void testSortByNameSameName() {
            Guest guest1 = new Guest("Alice", 25);
            Guest guest2 = new Guest("Alice", 30);
            users.add(guest2);
            users.add(guest1);

            List<User> sorted = UserSorter.sortByName(users);

            assertEquals(2, sorted.size());
            assertEquals("Alice", sorted.get(0).getName());
            assertEquals("Alice", sorted.get(1).getName());
        }

        @Test
        @DisplayName("Should handle users with different types")
        void testSortByNameMixedUserTypes() {
            users.add(new Admin("Zoe", 45));
            users.add(new Guest("Alice", 25));
            users.add(new Admin("Bob", 40));

            List<User> sorted = UserSorter.sortByName(users);

            assertEquals("Alice", sorted.get(0).getName());
            assertEquals("Bob", sorted.get(1).getName());
            assertEquals("Zoe", sorted.get(2).getName());
        }

        @Test
        @DisplayName("Should handle case-sensitive sorting")
        void testSortByNameCaseSensitive() {
            users.add(new Guest("alice", 25));
            users.add(new Guest("Alice", 30));

            List<User> sorted = UserSorter.sortByName(users);

            assertNotNull(sorted);
            assertEquals(2, sorted.size());
        }
    }

    @Nested
    @DisplayName("Sort By Age Tests")
    class SortByAgeTest {

        @Test
        @DisplayName("Should sort users by age in ascending order")
        void testSortByAgeAscending() {
            users.add(new Guest("Alice", 40));
            users.add(new Guest("Bob", 25));
            users.add(new Guest("Charlie", 35));

            List<User> sorted = UserSorter.sortByAge(users);

            assertEquals(25, sorted.get(0).getAge());
            assertEquals(35, sorted.get(1).getAge());
            assertEquals(40, sorted.get(2).getAge());
        }

        @Test
        @DisplayName("Should handle empty list")
        void testSortByAgeEmptyList() {
            List<User> sorted = UserSorter.sortByAge(users);
            assertTrue(sorted.isEmpty());
        }

        @Test
        @DisplayName("Should handle single user")
        void testSortByAgeSingleUser() {
            users.add(new Guest("Alice", 25));
            List<User> sorted = UserSorter.sortByAge(users);

            assertEquals(1, sorted.size());
            assertEquals(25, sorted.get(0).getAge());
        }

        @Test
        @DisplayName("Should preserve order of users with same age")
        void testSortByAgeSameAge() {
            Guest guest1 = new Guest("Alice", 30);
            Guest guest2 = new Guest("Bob", 30);
            users.add(guest2);
            users.add(guest1);

            List<User> sorted = UserSorter.sortByAge(users);

            assertEquals(2, sorted.size());
            assertEquals(30, sorted.get(0).getAge());
            assertEquals(30, sorted.get(1).getAge());
        }

        @Test
        @DisplayName("Should handle mixed user types")
        void testSortByAgeMixedUserTypes() {
            users.add(new Admin("Alice", 45));
            users.add(new Guest("Bob", 25));
            users.add(new Admin("Charlie", 35));

            List<User> sorted = UserSorter.sortByAge(users);

            assertEquals(25, sorted.get(0).getAge());
            assertEquals(35, sorted.get(1).getAge());
            assertEquals(45, sorted.get(2).getAge());
        }

        @Test
        @DisplayName("Should handle very young and very old users")
        void testSortByAgeExtremesAges() {
            users.add(new Guest("Old", 100));
            users.add(new Guest("Young", 1));
            users.add(new Guest("Middle", 50));

            List<User> sorted = UserSorter.sortByAge(users);

            assertEquals(1, sorted.get(0).getAge());
            assertEquals(50, sorted.get(1).getAge());
            assertEquals(100, sorted.get(2).getAge());
        }
    }

    @Nested
    @DisplayName("Min Function Tests")
    class MinFunctionTest {

        @Test
        @DisplayName("Should find user with minimum age")
        void testMinByAge() {
            users.add(new Guest("Alice", 30));
            users.add(new Guest("Bob", 20));
            users.add(new Guest("Charlie", 25));

            Optional<User> min = UserSorter.min(users, User::getAge);

            assertTrue(min.isPresent());
            assertEquals(20, min.get().getAge());
        }

        @Test
        @DisplayName("Should find user with minimum name length")
        void testMinByName() {
            users.add(new Guest("Alexander", 25));
            users.add(new Guest("Bob", 30));
            users.add(new Guest("Christopher", 28));

            Optional<User> min = UserSorter.min(users, u -> u.getName().length());

            assertTrue(min.isPresent());
            assertEquals(3, min.get().getName().length());
            assertEquals("Bob", min.get().getName());
        }

        @Test
        @DisplayName("Should return empty optional for empty list")
        void testMinEmptyList() {
            Optional<User> min = UserSorter.min(users, User::getAge);
            assertFalse(min.isPresent());
        }

        @Test
        @DisplayName("Should return optional with single user")
        void testMinSingleUser() {
            users.add(new Guest("Alice", 25));
            Optional<User> min = UserSorter.min(users, User::getAge);

            assertTrue(min.isPresent());
            assertEquals("Alice", min.get().getName());
        }
    }

    @Nested
    @DisplayName("Max Function Tests")
    class MaxFunctionTest {

        @Test
        @DisplayName("Should find user with maximum age")
        void testMaxByAge() {
            users.add(new Guest("Alice", 30));
            users.add(new Guest("Bob", 50));
            users.add(new Guest("Charlie", 25));

            Optional<User> max = UserSorter.max(users, User::getAge);

            assertTrue(max.isPresent());
            assertEquals(50, max.get().getAge());
        }

        @Test
        @DisplayName("Should find user with maximum name length")
        void testMaxByNameLength() {
            users.add(new Guest("Alice", 25));
            users.add(new Guest("Bob", 30));
            users.add(new Guest("Christopher", 28));

            Optional<User> max = UserSorter.max(users, u -> u.getName().length());

            assertTrue(max.isPresent());
            assertEquals(11, max.get().getName().length());
            assertEquals("Christopher", max.get().getName());
        }

        @Test
        @DisplayName("Should return empty optional for empty list")
        void testMaxEmptyList() {
            Optional<User> max = UserSorter.max(users, User::getAge);
            assertFalse(max.isPresent());
        }

        @Test
        @DisplayName("Should return optional with single user")
        void testMaxSingleUser() {
            users.add(new Guest("Alice", 25));
            Optional<User> max = UserSorter.max(users, User::getAge);

            assertTrue(max.isPresent());
            assertEquals("Alice", max.get().getName());
        }

        @Test
        @DisplayName("Should handle same maximum values")
        void testMaxSameValues() {
            users.add(new Guest("Alice", 50));
            users.add(new Guest("Bob", 50));
            users.add(new Guest("Charlie", 25));

            Optional<User> max = UserSorter.max(users, User::getAge);

            assertTrue(max.isPresent());
            assertEquals(50, max.get().getAge());
        }
    }

    @Nested
    @DisplayName("Combined Sorting Tests")
    class CombinedSortingTest {

        @Test
        @DisplayName("Should sort by name then by age")
        void testSortByNameThenAge() {
            users.add(new Guest("Bob", 30));
            users.add(new Guest("Alice", 25));
            users.add(new Guest("Alice", 35));

            List<User> sorted = UserSorter.sortByName(users);

            assertEquals("Alice", sorted.get(0).getName());
            assertEquals("Alice", sorted.get(1).getName());
            assertEquals("Bob", sorted.get(2).getName());
        }

        @Test
        @DisplayName("Should modify original list when sorting")
        void testSortModifiesOriginalList() {
            users.add(new Guest("Zoe", 25));
            users.add(new Guest("Alice", 30));

            UserSorter.sortByName(users);

            assertEquals("Alice", users.get(0).getName());
            assertEquals("Zoe", users.get(1).getName());
        }
    }
}
