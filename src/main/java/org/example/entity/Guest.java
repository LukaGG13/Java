package org.example.entity;

import java.time.LocalDateTime;

public final class Guest extends User implements GuestInterface{
   public Guest(String name, Integer age) {
       super(name, age);
   }

   @Override
   public Booking bookRoom(Room room, LocalDateTime checkIn, LocalDateTime checkOut){
       return new Booking(room, this, checkIn, checkOut);
   }
}