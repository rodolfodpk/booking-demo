package com.booking.core;

import com.booking.core.BookingData.Booking;

import java.util.Optional;

public interface BookingRepository {

    void createBooking(Booking booking);

    Optional<Booking> updateBooking(int bookingId, Booking booking);

    Optional<Booking> deleteBooking(int bookingId);

    Optional<Booking> getBooking(int bookingId);

}