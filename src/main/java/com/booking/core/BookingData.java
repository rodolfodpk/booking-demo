package com.booking.core;

import java.time.LocalDateTime;

public interface BookingData {

    record Guest(int id) {
    }


    record Property(int id) {
    }

    record Block(int id, Property property, LocalDateTime start, LocalDateTime end) {
    }

    record Booking(int id, Guest guest, Property property, LocalDateTime start, LocalDateTime end) {
    }

}