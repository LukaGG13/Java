package org.example.java.entity.admin;

import org.example.java.entity.room.Room;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("Admin Entity Tests")
class AdminTest {

    @Nested
    @DisplayName("Admin Creation Tests")
    class AdminCreationTest {

        @Test
        @DisplayName("Should create admin with auto-generated UUID")
        void testAdminCreationAutoId() {
            Admin admin = new Admin("Manager", 45);

            assertNotNull(admin.getId());
            assertEquals("Manager", admin.getName());
            assertEquals(45, admin.getAge());
        }

        @Test
        @DisplayName("Should create admin with specific UUID")
        void testAdminCreationWithId() {
            UUID id = UUID.randomUUID();
            Admin admin = new Admin(id, "Admin", 50);

            assertEquals(id, admin.getId());
            assertEquals("Admin", admin.getName());
            assertEquals(50, admin.getAge());
        }

        @Test
        @DisplayName("Each admin should have unique UUID")
        void testAdminUniqueIds() {
            Admin admin1 = new Admin("Manager1", 40);
            Admin admin2 = new Admin("Manager2", 45);

            assertNotEquals(admin1.getId(), admin2.getId());
        }
    }

    /*
    @Nested
    @DisplayName("Admin Room Creation Tests")
    class AdminRoomCreationTest {

        private Admin admin;

        @BeforeEach
        void setUp() {
            admin = new Admin("TestAdmin", 40);
        }

        @Test
        @DisplayName("Should create room without builder modifications")
        void testCreateRoomBasic() {
            Room room = admin.createRoom(2, BigDecimal.valueOf(100));

            assertNotNull(room);
            assertEquals(2, room.getNumOfBeds());
            assertEquals(BigDecimal.valueOf(100), room.getPricePerNight());
        }

        @Test
        @DisplayName("Should create room with custom builder")
        void testCreateRoomWithMockInputService() {
            AdminInputService mockService = org.mockito.Mockito.mock(AdminInputService.class);
            
            Room room = admin.createRoom(3, BigDecimal.valueOf(150), mockService);

            assertNotNull(room);
            assertEquals(3, room.getNumOfBeds());
            assertEquals(BigDecimal.valueOf(150), room.getPricePerNight());
        }

        @Test
        @DisplayName("Should create multiple rooms")
        void testCreateMultipleRooms() {
            Room room1 = admin.createRoom(2, BigDecimal.valueOf(100));
            Room room2 = admin.createRoom(3, BigDecimal.valueOf(150));
            Room room3 = admin.createRoom(1, BigDecimal.valueOf(75));

            assertEquals(2, room1.getNumOfBeds());
            assertEquals(3, room2.getNumOfBeds());
            assertEquals(1, room3.getNumOfBeds());
        }

        @Test
        @DisplayName("Should create room with various bed counts")
        void testCreateRoomVariousBeds() {
            Room room1 = admin.createRoom(1, BigDecimal.valueOf(50));
            Room room2 = admin.createRoom(4, BigDecimal.valueOf(200));
            Room room3 = admin.createRoom(8, BigDecimal.valueOf(400));

            assertEquals(1, room1.getNumOfBeds());
            assertEquals(4, room2.getNumOfBeds());
            assertEquals(8, room3.getNumOfBeds());
        }

        @Test
        @DisplayName("Should create room with various prices")
        void testCreateRoomVariousPrices() {
            Room room1 = admin.createRoom(2, BigDecimal.valueOf(50.00));
            Room room2 = admin.createRoom(2, BigDecimal.valueOf(100.50));
            Room room3 = admin.createRoom(2, BigDecimal.valueOf(999.99));

            assertEquals(BigDecimal.valueOf(50.00), room1.getPricePerNight());
            assertEquals(BigDecimal.valueOf(100.50), room2.getPricePerNight());
            assertEquals(BigDecimal.valueOf(999.99), room3.getPricePerNight());
        }
    }

     */

    /*
    @Nested
    @DisplayName("Admin Interface Implementation Tests")
    class AdminInterfaceTest {

        @Test
        @DisplayName("Admin should implement AdminInterface")
        void testAdminImplementsInterface() {
            Admin admin = new Admin("Manager", 45);
            assertTrue(admin instanceof AdminInterface);
        }

        @Test
        @DisplayName("Admin should have createRoom method from interface")
        void testCreateRoomFromInterface() {
            Admin admin = new Admin("Manager", 45);
            AdminInterface adminInterface = admin;

            Room room = adminInterface.createRoom(2, BigDecimal.valueOf(100));
            assertNotNull(room);
        }
    }

     */
    @Nested
    @DisplayName("Admin Properties Tests")
    class AdminPropertiesTest {

        @Test
        @DisplayName("Should inherit User properties")
        void testInheritUserProperties() {
            Admin admin = new Admin("TestAdmin", 40);

            assertEquals("TestAdmin", admin.getName());
            assertEquals(40, admin.getAge());
            assertNotNull(admin.getId());
        }

        @Test
        @DisplayName("Should have correct toString")
        void testToString() {
            Admin admin = new Admin("Manager", 50);
            String toString = admin.toString();

            assertTrue(toString.contains("Manager"));
            assertTrue(toString.contains("50"));
        }

        @Test
        @DisplayName("Should be searchable")
        void testSearchable() {
            Admin admin = new Admin("AdminName", 45);
            var keywords = admin.getKeyWord();

            assertNotNull(keywords);
            assertFalse(keywords.isEmpty());
        }
    }

    @Nested
    @DisplayName("Admin Equality Tests")
    class AdminEqualityTest {

        @Test
        @DisplayName("Admins with same name and age should be equal")
        void testAdminEquality() {
            Admin admin1 = new Admin("Manager", 40);
            Admin admin2 = new Admin("Manager", 40);

            assertEquals(admin1, admin2);
        }

        @Test
        @DisplayName("Admins with different names should not be equal")
        void testAdminInequalityName() {
            Admin admin1 = new Admin("Manager1", 40);
            Admin admin2 = new Admin("Manager2", 40);

            assertNotEquals(admin1, admin2);
        }

        @Test
        @DisplayName("Admins with different ages should not be equal")
        void testAdminInequalityAge() {
            Admin admin1 = new Admin("Manager", 40);
            Admin admin2 = new Admin("Manager", 50);

            assertNotEquals(admin1, admin2);
        }
    }

    @Nested
    @DisplayName("Admin Room Builder Tests")
    class AdminRoomBuilderTest {

        private Admin admin;

        @BeforeEach
        void setUp() {
            admin = new Admin("TestAdmin", 40);
        }

        @Test
        @DisplayName("Should build room with basic parameters")
        void testBuildRoomBasic() {
            Room.RoomBuilder builder = new Room.RoomBuilder(2, BigDecimal.valueOf(100));
            Room room = builder.build();

            assertNotNull(room);
            assertEquals(2, room.getNumOfBeds());
        }

        @Test
        @DisplayName("Should build room through admin buildRoom method with mock")
        void testAdminBuildRoomMock() {
            AdminInputService mockService = org.mockito.Mockito.mock(AdminInputService.class);
            Room.RoomBuilder builder = new Room.RoomBuilder(3, BigDecimal.valueOf(150));

            when(mockService.askInteger(anyString())).thenReturn(5);
            when(mockService.askBigDecimal(anyString())).thenReturn(BigDecimal.valueOf(10));
            when(mockService.askRoomAmenity(anyString())).thenReturn(Room.Amenity.WIFI);

            Room.RoomBuilder builtBuilder = admin.buildRoom(builder, mockService);
            Room room = builtBuilder.build();

            assertNotNull(room);
            assertEquals(3, room.getNumOfBeds());
            assertEquals(BigDecimal.valueOf(150), room.getPricePerNight());
        }
    }

    @Nested
    @DisplayName("Admin Type Tests")
    class AdminTypeTest {

        @Test
        @DisplayName("Admin should be instance of User")
        void testAdminIsUser() {
            Admin admin = new Admin("Manager", 45);
            assertTrue(admin instanceof org.example.java.entity.user.User);
        }

        @Test
        @DisplayName("Admin should be distinct from Guest")
        void testAdminNotGuest() {
            Admin admin = new Admin("Admin", 40);
            org.example.java.entity.guest.Guest guest = new org.example.java.entity.guest.Guest("Guest", 40);

            assertNotEquals(admin.getClass(), guest.getClass());
        }
    }
}
