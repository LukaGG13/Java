package org.example.utils.menus;

import org.example.entity.admin.Admin;
import org.example.entity.admin.AdminInputServiceConsole;
import org.example.entity.repository.Repository;
import org.example.entity.room.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.IO.readln;

//TODO javadoc

/**
 * Class to display an admin menu in the console
 * @version 1.0
 * @author luka
 */
public class AdminMenu implements MenuInterface {
    private final List<String> choices = List.of("1) Create room",
                                           "2) Log out");
    private Integer chosenOption = choices.size();
    private static final Logger log = LoggerFactory.getLogger(AdminMenu.class);
    private final Repository repository;

    /**
     * Constructs a new {@link AdminMenu}.
     * @param repository a {@link Repository} to access {@link Room}'s, {@link org.example.entity.user.User}'s, and {@link org.example.entity.booking.Booking}'s.
     */
    public AdminMenu(Repository repository){
        this.repository = repository;
    }

    @Override
    public void printMenu() {
        choices.forEach(IO::println);
    }

    @Override
    public void askChoice() {
        while(true){
            try {
                chosenOption = Integer.parseInt(readln("Select option: "));
                if (1 <= chosenOption && chosenOption < choices.size() + 1){
                    break;
                }
            } catch (NumberFormatException e){
               log.error("A parsing error in Admin menu", e);
            }
        }
    }

    /**
     * Create a {@link Room}
     * @return {@link Room}.
     */
    private Room choiceCreateRoom() {
        if (repository.getActiveUser().orElseThrow() instanceof Admin admin) {
            var service = new AdminInputServiceConsole();
            Integer numberOfBeds = service.askInteger("Enter the number of beds: ");
            BigDecimal pricePerNight = service.askBigDecimal("Enter the price per night: ");
            return admin.createRoom(numberOfBeds, pricePerNight);
        } else {
            throw new IllegalStateException("Trying to crate room while a non admin users is active.");
        }
    }

    @Override
    public MenuResult doChoice() {
        return switch (chosenOption) {
            case 1 -> new MenuResult.RoomCreated(choiceCreateRoom());
            case 2 -> new MenuResult.LoggedOut();
            default -> new MenuResult.LoggedOut();
        };
    }
}
