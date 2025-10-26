package entity;

import java.math.BigDecimal;

public sealed interface AdminInterface permits Admin{
    Room createRoom(Integer numberOfBeds, BigDecimal pricePerNight);
}
