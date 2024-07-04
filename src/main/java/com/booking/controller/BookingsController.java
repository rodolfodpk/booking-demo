package com.booking.controller;

import com.booking.core.BlockRepository;
import com.booking.core.BookingData.Booking;
import com.booking.core.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingsController {

    private final BookingRepository bookingRepository;
    private final BlockRepository blockRepository;

    @Autowired
    BookingsController(BookingRepository bookingRepository, BlockRepository blockRepository) {
        this.bookingRepository = bookingRepository;
        this.blockRepository = blockRepository;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Booking booking) {
        if (blockRepository.isBlocked(booking)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        bookingRepository.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(@PathVariable int id) {
        var booking = bookingRepository.getBooking(id);
        return booking.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Booking booking) {
        var existingBooking = bookingRepository.updateBooking(id, booking);
        if (existingBooking.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        var existingBooking = bookingRepository.deleteBooking(id);
        if (existingBooking.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
