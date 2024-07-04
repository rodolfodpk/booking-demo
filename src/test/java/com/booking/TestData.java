package com.booking;

import com.booking.core.BookingData.Block;
import com.booking.core.BookingData.Booking;
import com.booking.core.BookingData.Guest;
import com.booking.core.BookingData.Property;

import java.time.LocalDateTime;

public interface TestData {

    Guest guest1 = new Guest(1);

    Property property1 = new Property(1);

    Booking booking1 = new Booking(1,
            guest1,
            property1,
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(1)
    );

    Block block1 = new Block(1, property1, booking1.start(), booking1.end());

}
