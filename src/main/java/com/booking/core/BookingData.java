package com.booking.core;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public interface BookingData {

    record Guest(int id) {
    }

    record Property(int id) {
    }

    record Block(int id, @NotNull Property property,
                 @NotNull LocalDateTime start, @NotNull LocalDateTime end) {
    }

    record Booking(int id, @NotNull Guest guest, @NotNull Property property,
                   @NotNull LocalDateTime start, @NotNull LocalDateTime end) {
    }

}