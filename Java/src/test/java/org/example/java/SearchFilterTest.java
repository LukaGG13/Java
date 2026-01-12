package org.example.java;

import javafx.util.Pair;
import org.example.java.entity.interfaces.Searchable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Set;


class TestSearchable implements Searchable {
    private final Set<Pair<String, String>> keywords;

    TestSearchable(Set<Pair<String, String>> keywords) {
        this.keywords = keywords;
    }

    @Override
    public Set<Pair<String, String>> getKeyWord() {
        return keywords;
    }
}

public class SearchFilterTest {

    // This is the logic extracted from your filter() method
    private boolean matches(Searchable o, Set<Pair<String, String>> searchQueries) {
        if (searchQueries.isEmpty()) {
            return true;
        }

        for (var query : searchQueries) {
            boolean match = false;
            for (var keyWord : o.getKeyWord()) {
                if ((keyWord.getKey().equals(query.getKey()) || "Any".equals(query.getKey()))
                        && keyWord.getValue().contains(query.getValue())) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                return false;
            }
        }
        return true;
    }

    @Test
    void emptySearchMatchesEverything() {
        var searchable = new TestSearchable(Set.of(
                new Pair<>("Class", "Room")
        ));

        assertTrue(matches(searchable, Set.of()));
    }

    @Test
    void matchesByExactKey() {
        var searchable = new TestSearchable(Set.of(
                new Pair<>("Class", "Room"),
                new Pair<>("numOfBeds", "2")
        ));

        var query = Set.of(
                new Pair<>("numOfBeds", "2")
        );

        assertTrue(matches(searchable, query));
    }

    @Test
    void failsIfOneQueryDoesNotMatch() {
        var searchable = new TestSearchable(Set.of(
                new Pair<>("Class", "Room"),
                new Pair<>("numOfBeds", "2")
        ));

        var query = Set.of(
                new Pair<>("numOfBeds", "2"),
                new Pair<>("WIFI", "WIFI")
        );

        assertFalse(matches(searchable, query));
    }

    @Test
    void anyKeyMatchesAnyField() {
        var searchable = new TestSearchable(Set.of(
                new Pair<>("pricePerNight", "120.00")
        ));

        var query = Set.of(
                new Pair<>("Any", "120")
        );

        assertTrue(matches(searchable, query));
    }

    @Test
    void partialValueMatchWorks() {
        var searchable = new TestSearchable(Set.of(
                new Pair<>("distanceFromBeach", "0.35")
        ));

        var query = Set.of(
                new Pair<>("distanceFromBeach", "0.3")
        );

        assertTrue(matches(searchable, query));
    }
}