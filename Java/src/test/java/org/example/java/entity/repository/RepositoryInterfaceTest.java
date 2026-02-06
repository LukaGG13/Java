package org.example.java.entity.repository;

import org.example.java.entity.admin.Admin;
import org.example.java.entity.booking.Booking;
import org.example.java.entity.guest.Guest;
import org.example.java.entity.review.Review;
import org.example.java.entity.room.Room;
import org.example.java.entity.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Repository Interface Tests")
class RepositoryInterfaceTest {

    private Repository repository;
    private List<Room> mockRooms;
    private List<User> mockUsers;
    private List<Booking> mockBookings;
    private Map<Room, List<Review>> mockReviews;

    @BeforeEach
    void setUp() {
        repository = mock(Repository.class);
        
        mockRooms = new ArrayList<>();
        mockUsers = new ArrayList<>();
        mockBookings = new ArrayList<>();
        mockReviews = new HashMap<>();

        when(repository.getRooms()).thenReturn(mockRooms);
        when(repository.getUsers()).thenReturn(mockUsers);
        when(repository.getBookings()).thenReturn(mockBookings);
        when(repository.getReviews()).thenReturn(mockReviews);
    }

    @Test
    @DisplayName("Should get empty rooms list initially")
    void testGetRoomsEmpty() {
        List<Room> rooms = repository.getRooms();

        assertNotNull(rooms);
        assertTrue(rooms.isEmpty());
    }

    @Test
    @DisplayName("Should get empty users list initially")
    void testGetUsersEmpty() {
        List<User> users = repository.getUsers();

        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    @DisplayName("Should get empty bookings list initially")
    void testGetBookingsEmpty() {
        List<Booking> bookings = repository.getBookings();

        assertNotNull(bookings);
        assertTrue(bookings.isEmpty());
    }

    @Test
    @DisplayName("Should get empty reviews map initially")
    void testGetReviewsEmpty() {
        Map<Room, List<Review>> reviews = repository.getReviews();

        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());
    }

    @Test
    @DisplayName("Should get active user as optional")
    void testGetActiveUser() {
        User mockUser = new Guest("User", 30);
        when(repository.getActiveUser()).thenReturn(Optional.of(mockUser));

        Optional<User> activeUser = repository.getActiveUser();

        assertTrue(activeUser.isPresent());
        assertEquals(mockUser, activeUser.get());
    }

    @Test
    @DisplayName("Should return empty optional if no active user")
    void testGetActiveUserEmpty() {
        when(repository.getActiveUser()).thenReturn(Optional.empty());

        Optional<User> activeUser = repository.getActiveUser();

        assertFalse(activeUser.isPresent());
    }

    @Test
    @DisplayName("Should add user to repository")
    void testAddUser() {
        User user = new Guest("NewGuest", 25);
        mockUsers.add(user);

        List<User> users = repository.getUsers();

        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
    }

    @Test
    @DisplayName("Should add multiple users")
    void testAddMultipleUsers() {
        User user1 = new Guest("Guest1", 25);
        User user2 = new Admin("Admin1", 40);
        mockUsers.add(user1);
        mockUsers.add(user2);

        List<User> users = repository.getUsers();

        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("Should update user")
    void testUpdateUser() {
        User user = new Guest("Original", 25);
        mockUsers.add(user);

        doNothing().when(repository).updateUser(user);
        repository.updateUser(user);

        verify(repository).updateUser(user);
    }

    @Test
    @DisplayName("Should delete user")
    void testDeleteUser() {
        User user = new Guest("ToDelete", 30);
        mockUsers.add(user);

        doNothing().when(repository).deleteUser(user);
        repository.deleteUser(user);

        verify(repository).deleteUser(user);
    }

    @Test
    @DisplayName("Should add room to repository")
    void testAddRoom() {
        Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
        mockRooms.add(room);

        doNothing().when(repository).addRoom(room);
        repository.addRoom(room);

        verify(repository).addRoom(room);
    }

    @Test
    @DisplayName("Should handle multiple rooms")
    void testMultipleRooms() {
        Room room1 = new Room.RoomBuilder(1, BigDecimal.valueOf(50)).build();
        Room room2 = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
        Room room3 = new Room.RoomBuilder(3, BigDecimal.valueOf(150)).build();
        
        mockRooms.add(room1);
        mockRooms.add(room2);
        mockRooms.add(room3);

        List<Room> rooms = repository.getRooms();

        assertEquals(3, rooms.size());
    }

    @Test
    @DisplayName("Should handle bookings collection")
    void testBookingsCollection() {
        Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
        Guest guest = new Guest("Guest", 30);
        Booking booking = new Booking(room, guest, 
                LocalDateTime.now().plusDays(1), 
                LocalDateTime.now().plusDays(3));
        
        mockBookings.add(booking);

        List<Booking> bookings = repository.getBookings();

        assertEquals(1, bookings.size());
        assertEquals(booking, bookings.get(0));
    }

    @Test
    @DisplayName("Should handle reviews per room")
    void testReviewsPerRoom() {
        Room room1 = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
        Room room2 = new Room.RoomBuilder(3, BigDecimal.valueOf(150)).build();

        Guest guest1 = new Guest("Guest1", 30);
        Guest guest2 = new Guest("Guest2", 35);

        Review review1 = new Review(guest1, "Good", LocalDate.now(), 8);
        Review review2 = new Review(guest2, "Great", LocalDate.now(), 9);

        mockReviews.put(room1, Arrays.asList(review1));
        mockReviews.put(room2, Arrays.asList(review2));

        Map<Room, List<Review>> reviews = repository.getReviews();

        assertEquals(2, reviews.size());
        assertTrue(reviews.containsKey(room1));
        assertTrue(reviews.containsKey(room2));
    }

    @Test
    @DisplayName("Should retrieve user by collection operations")
    void testUserRetrieval() {
        User user1 = new Guest("Alice", 25);
        User user2 = new Guest("Bob", 30);
        mockUsers.add(user1);
        mockUsers.add(user2);

        List<User> users = repository.getUsers();
        Optional<User> found = users.stream().filter(u -> u.getName().equals("Alice")).findFirst();

        assertTrue(found.isPresent());
        assertEquals("Alice", found.get().getName());
    }

    @Test
    @DisplayName("Should verify repository method invocations")
    void testRepositoryMethodCalls() {
        repository.getRooms();
        repository.getUsers();
        repository.getBookings();
        repository.getReviews();
        repository.getActiveUser();

        verify(repository).getRooms();
        verify(repository).getUsers();
        verify(repository).getBookings();
        verify(repository).getReviews();
        verify(repository).getActiveUser();
    }

    @Test
    @DisplayName("Should handle room collection filtering")
    void testRoomFiltering() {
        Room cheapRoom = new Room.RoomBuilder(1, BigDecimal.valueOf(30)).build();
        Room expensiveRoom = new Room.RoomBuilder(4, BigDecimal.valueOf(500)).build();
        
        mockRooms.add(cheapRoom);
        mockRooms.add(expensiveRoom);

        List<Room> rooms = repository.getRooms();
        Optional<Room> expensive = rooms.stream()
                .filter(r -> r.getPricePerNight().compareTo(BigDecimal.valueOf(200)) > 0)
                .findFirst();

        assertTrue(expensive.isPresent());
        assertEquals(expensiveRoom, expensive.get());
    }

    @Test
    @DisplayName("Should preserve booking associations")
    void testBookingAssociations() {
        Room room = new Room.RoomBuilder(2, BigDecimal.valueOf(100)).build();
        Guest guest = new Guest("Guest", 30);
        LocalDateTime checkIn = LocalDateTime.now().plusDays(1);
        LocalDateTime checkOut = LocalDateTime.now().plusDays(3);
        
        Booking booking = new Booking(room, guest, checkIn, checkOut);
        mockBookings.add(booking);

        List<Booking> bookings = repository.getBookings();
        Booking retrieved = bookings.get(0);

        assertEquals(room, retrieved.room());
        assertEquals(guest, retrieved.user());
    }
}
