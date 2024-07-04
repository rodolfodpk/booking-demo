package com.booking.impl;

import com.booking.core.BookingData.Booking;
import com.booking.core.BookingRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemBookingRepository implements BookingRepository {

    private final Map<Integer, Map<Integer, Booking>> bookingsPerProperty = new HashMap<>();

    @Override
    public void createBooking(Booking booking) {
        var bookingMap = new HashMap<Integer, Booking>();
        bookingMap.put(booking.id(), booking);
        bookingsPerProperty.put(booking.property().id(), bookingMap);
    }

    @Override
    public Optional<Booking> updateBooking(int bookingId, Booking booking) {
        if (bookingsPerProperty.containsKey(bookingId)) {
            bookingsPerProperty.get(bookingId).put(bookingId, booking);
            return Optional.of(booking);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Booking> deleteBooking(int bookingId) {
        var bookingOptional = bookingsPerProperty.values().stream()
                .filter(booking -> booking.containsKey(bookingId))
                .findFirst()
                .map(bookings -> bookings.get(bookingId));
        bookingOptional.flatMap(booking -> bookingsPerProperty.values().stream()
                .filter(bookings -> bookings.containsKey(bookingId))
                .findFirst()).ifPresent(bookings -> bookings.remove(bookingId));
        return bookingOptional;
    }

    @Override
    public Optional<Booking> getBooking(int bookingId) {
        return bookingsPerProperty.values().stream()
                .flatMap(bookingMap -> bookingMap.values().stream())
                .filter(booking -> booking.id() == bookingId)
                .findFirst();
    }

}