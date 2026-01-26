# Testing Guidelines & Best Practices

## Overview
This document outlines the testing strategy and best practices for the Java Hotel Management System.

## Test Organization

### Directory Structure
```
src/test/java/org/example/java/
├── entity/
│   ├── UserTest.java
│   ├── RoomTest.java
│   ├── BookingTest.java
│   ├── ReviewTest.java
│   ├── SearchableTest.java
│   ├── EdgeCasesAndBoundaryTest.java
│   ├── admin/
│   │   └── AdminTest.java
│   └── repository/
│       └── RepositoryInterfaceTest.java
├── utils/
│   ├── UserSorterTest.java
│   └── ControllerUtilsTest.java
├── exception/
│   └── DatabaseExceptionTest.java
├── DataBaseUtilsTest.java
├── SearchFilterTest.java
├── SimpleTest.java
└── IntegrationTest.java
```

## Testing Conventions

### 1. Naming Conventions
- Test class names end with `Test` (e.g., `UserTest.java`)
- Test method names use `test` prefix: `testFeatureName()`
- Use camelCase for test method names
- Test names should be descriptive: `testSortByNameAlphabetically()`

### 2. Test Class Structure
```java
@DisplayName("Entity Name Tests")
class EntityNameTest {
    
    @Nested
    @DisplayName("Feature Group Tests")
    class FeatureGroupTest {
        @BeforeEach
        void setUp() { /* initialization */ }
        
        @Test
        @DisplayName("Should do something specific")
        void testSpecificBehavior() { /* test */ }
    }
}
```

### 3. AAA Pattern (Arrange-Act-Assert)
All tests follow the AAA pattern:
```java
@Test
void testExample() {
    // Arrange - Set up test data
    User user = new Guest("Test", 25);
    
    // Act - Perform the action
    String name = user.getName();
    
    // Assert - Verify the result
    assertEquals("Test", name);
}
```

### 4. Assertions
- Use appropriate assertion methods
- Include descriptive messages: `assertEquals(expected, actual, "message")`
- Prefer specific assertions over generic ones
- Use `assertThrows()` for exception testing

### 5. BeforeEach Initialization
Use `@BeforeEach` for common setup:
```java
@BeforeEach
void setUp() {
    room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
    guest = new Guest("TestGuest", 30);
}
```

## Test Categories

### Unit Tests
- Test individual components in isolation
- Mock external dependencies
- Fast execution
- Located in component-specific test classes

### Integration Tests
- Test component interactions
- Use real objects where practical
- Verify end-to-end workflows
- Located in `IntegrationTest.java`

### Edge Case Tests
- Test boundary values
- Test special characters and Unicode
- Test extreme values
- Located in `EdgeCasesAndBoundaryTest.java`

## Using Mockito

### Mocking Services
```java
AdminInputService mockService = mock(AdminInputService.class);
when(mockService.askInteger(anyString())).thenReturn(5);
```

### Verifying Calls
```java
repository.addUser(user);
verify(repository).addUser(user);
```

## Parameterized Tests

Use `@ParameterizedTest` for testing multiple values:
```java
@ParameterizedTest
@ValueSource(ints = {1, 2, 3, 4, 5})
void testValidBedCounts(int beds) {
    Room room = new Room.RoomBuilder(beds, price).build();
    assertEquals(beds, room.getNumOfBeds());
}
```

## Testing Best Practices

### 1. Keep Tests Simple
- One assertion concept per test
- Clear setup and execution
- Avoid complex logic in tests

### 2. Use Descriptive Names
- Test names should explain what is being tested
- Use @DisplayName for complex scenarios
- Include "Should" in names when possible

### 3. DRY (Don't Repeat Yourself)
- Use @BeforeEach for common setup
- Extract common assertions to helper methods
- Use nested test classes for related tests

### 4. Test Independence
- Each test should be independent
- No shared mutable state
- Use fresh instances in each test

### 5. Avoid Test Anti-patterns
- ❌ Don't test private methods directly
- ❌ Don't use Thread.sleep() for timing
- ❌ Don't test implementation details
- ❌ Don't create interdependent tests

### 6. Exception Testing
```java
@Test
@DisplayName("Should throw IllegalArgumentException")
void testInvalidInput() {
    assertThrows(IllegalArgumentException.class,
            () -> new Booking(null, guest, checkIn, checkOut),
            "Message should be null");
}
```

## Coverage Goals

### Target Coverage
- Overall: 80%+
- Business Logic: 90%+
- Utilities: 85%+
- Edge Cases: 75%+

### Running Coverage Reports
```bash
mvn clean test jacoco:report
# Report generated at: target/site/jacoco/index.html
```

## Common Testing Scenarios

### Testing Entity Creation
```java
@Test
void testEntityCreation() {
    User user = new Guest("Name", 30);
    assertNotNull(user.getId());
    assertEquals("Name", user.getName());
    assertEquals(30, user.getAge());
}
```

### Testing Builder Pattern
```java
@Test
void testBuilderChaining() {
    Room room = new Room.RoomBuilder(2, price)
            .sizeInSqrM(30)
            .addAmenity(Room.Amenity.WIFI)
            .build();
    assertNotNull(room);
}
```

### Testing Collections
```java
@Test
void testCollectionOperations() {
    List<User> users = new ArrayList<>();
    users.add(new Guest("A", 25));
    users.add(new Guest("B", 30));
    
    assertEquals(2, users.size());
    assertTrue(users.stream().anyMatch(u -> u.getName().equals("A")));
}
```

### Testing Equality
```java
@Test
void testEquality() {
    Guest guest1 = new Guest("Alice", 25);
    Guest guest2 = new Guest("Alice", 25);
    
    assertEquals(guest1, guest2);
    assertEquals(guest1.hashCode(), guest2.hashCode());
}
```

## Debugging Tests

### Running Single Test
```bash
mvn test -Dtest=UserTest#testUserCreation
```

### Running Test Class
```bash
mvn test -Dtest=UserTest
```

### Running Nested Test
```bash
mvn test -Dtest=RoomTest#RoomBuilderTest#testBuilderChaining
```

### Running Tests Matching Pattern
```bash
mvn test -Dtest=*Sorter*
```

### Verbose Output
```bash
mvn test -X
```

## Test Maintenance

### When to Update Tests
- ✅ When adding new features
- ✅ When fixing bugs (add regression test)
- ✅ When changing public APIs
- ✅ When improving edge case coverage
- ✅ When refactoring code

### What NOT to Change
- ❌ Don't weaken existing tests
- ❌ Don't remove working tests
- ❌ Don't skip flaky tests
- ❌ Don't ignore test failures

## Continuous Integration

### Pre-commit
Run tests locally before committing:
```bash
mvn clean test
```

### CI Pipeline
Tests should be run:
- On every commit
- Before merge requests
- On release branches
- With full coverage reports

## Test Documentation

### Test Documentation Template
```java
/**
 * Tests for [Feature Name]
 * 
 * Tests verify:
 * - Feature A
 * - Feature B
 * 
 * Edge cases covered:
 * - Case 1
 * - Case 2
 */
class FeatureTest { }
```

## Performance Testing

### Keeping Tests Fast
- Unit tests should run in < 1 second
- Integration tests should run in < 5 seconds
- Avoid I/O operations in unit tests
- Use mocks instead of real databases

### Timeout Testing
```java
@Test
@Timeout(2)  // 2 seconds
void testPerformance() {
    // test that should complete quickly
}
```

## Common Issues and Solutions

### Issue: Tests Pass Locally but Fail in CI
- Check for date/time dependencies
- Verify timezone handling
- Check for hardcoded paths
- Ensure no environmental assumptions

### Issue: Flaky Tests
- Avoid Thread.sleep()
- Don't depend on timing
- Use appropriate wait strategies
- Isolate external dependencies

### Issue: Test Too Slow
- Reduce dataset size
- Use mocks instead of real objects
- Parallelize test execution
- Remove unnecessary I/O

## Resources

- [JUnit 5 Documentation](https://junit.org/junit5/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core)
- [AssertJ Documentation](https://assertj.github.io/assertj-core-features-highlight)
- [Testing Best Practices](https://google.github.io/styleguide/javaguide.html#s4.8-testing)
