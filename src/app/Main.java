import entity.Booking;
import entity.Room;
import entity.User;

void displayUsers (User[] users) {
    for (Integer i = 0; i < users.length; i++) {
        System.out.println((i + 1) + ": " + users[i].getName() + " " + users[i].getAge());
    }
}

void displayRooms (Room[] rooms) {
    for (Integer i = 0; i < rooms.length; i++) {
        System.out.println((i + 1) +": Number of beds: " + rooms[i].getNumOfBeds() + " Size in m^2: " + rooms[i].getSizeInSqrM());
    }
}

void main() {
    Integer n = 5;
    Room[] rooms = new Room[n];
    User[] users = new User[n];
    Booking[] bookings = new Booking[n];
    Scanner sc = new Scanner(System.in);

    System.out.println("Enter " + n + " users");
    for (Integer i = 0; i < n; i++) {
       String userName;
       System.out.print("Enter name of user: ");
       userName = sc.nextLine();

       Integer userAge;
       System.out.print("Enter age of user: ");
       userAge = sc.nextInt();
       sc.nextLine();

       users[i] = new User(userName, userAge);
    }

    System.out.println("Enter " + n + " rooms");
    for (Integer i = 0; i < n; i++) {
        Integer numOfBeds;
        System.out.print("Enter number of beds: ");
        numOfBeds = sc.nextInt();
        sc.nextLine();

        Integer sizeInSqM;
        System.out.print("Enter size of the room in square meters: ");
        sizeInSqM = sc.nextInt();
        sc.nextLine();

        rooms[i] = new Room(numOfBeds, sizeInSqM);
    }

    System.out.println("Enter " + n + " booking");
    for (Integer i = 0; i < n; i++) {
        Integer idxRoom;
        System.out.println("Select one room to be booked: ");
        displayRooms(rooms);
        idxRoom = sc.nextInt();
        sc.nextLine();

        Integer idxUser;
        System.out.println("Select one used to book the room: ");
        displayUsers(users);
        idxUser = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter check in date: ");
        LocalDate checkIn = LocalDate.parse(sc.nextLine());

        System.out.println("Enter check out date: ");
        LocalDate checkOut = LocalDate.parse(sc.nextLine());

        bookings[i] = new Booking(rooms[idxRoom - 1], users[idxUser - 1], checkIn, checkOut);
    }

    while (true) {
        System.out.println("Search:");
        System.out.println("1) Users 2) Rooms 3) Bookings");
        Integer query = sc.nextInt();
        sc.nextLine();
        if (query == 1) {
            System.out.println("1) Name 2) Age");
            query = sc.nextInt();
            sc.nextLine();

            if (query == 1) {
                System.out.println("Enter name to be searched or max or min");
            }
            if (query == 2) {
                System.out.println("Enter age to be searched or max or min");
            }
            String pattern = sc.nextLine();
            User result = users[0];

            for (Integer i = 0; i < n; i++) {
                if ("min".equals(pattern)) {
                    if (query == 1 && result.getName().compareTo(users[i].getName()) > 0) {
                        result = users[i];
                    }
                    if (query == 2 && result.getAge() > users[i].getAge()) {
                        result = users[i];
                    }
                    continue;
                }
                if ("max".equals(pattern)){
                    if (query == 1 && result.getName().compareTo(users[i].getName()) < 0) {
                        result = users[i];
                    }
                    if (query == 2 && result.getAge() < users[i].getAge()) {
                        result = users[i];
                    }
                    continue;
                }

                if (query == 1 && users[i].getName().equals(pattern)) {
                    System.out.println(users[i].getName() + " " + users[i].getAge());
                }
                if (query == 2 && users[i].getAge().equals(Integer.parseInt(pattern))) {
                    System.out.println(users[i].getName() + " " + users[i].getAge());
                }
            }

            if ("min".equals(pattern) || "max".equals(pattern)) {
                System.out.println(result.getName() + " " + result.getAge());
            }
        }
    }
}