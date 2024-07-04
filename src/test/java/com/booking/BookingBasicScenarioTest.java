package com.booking;

import com.booking.core.BookingData.Booking;
import com.booking.core.BookingData.Guest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static com.booking.TestData.booking1;
import static com.booking.TestData.property1;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class BookingBasicScenarioTest {

    private final Guest guest2 = new Guest(2);
    private final Booking updatedBooking1 = new Booking(1,
            guest2,
            property1,
            booking1.start(),
            booking1.end()
    );

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void whenNoBookings() {
        var response = restTemplate.getForEntity("/bookings/{id}", Booking.class, 1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(2)
    public void whenAddingNewBooking() {
        var response = restTemplate.postForEntity("/bookings", booking1, Booking.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(3)
    public void thenThereIsOneBooking() {
        var response = restTemplate.getForEntity("/bookings/{id}", Booking.class, 1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(booking1);
    }

    @Test
    @Order(4)
    public void whenUpdatingBooking() {
        restTemplate.put("/bookings/{id}", updatedBooking1, 1);
    }

    @Test
    @Order(5)
    public void thenBookingIsUpdated() {
        var response = restTemplate.getForEntity("/bookings/{id}", Booking.class, 1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedBooking1);
    }

    @Test
    @Order(6)
    public void whenDeletingBooking() {
        restTemplate.delete("/bookings/{id}", 1);
    }

    @Test
    @Order(7)
    public void thenThereArentAnyBooking() {
        var response = restTemplate.getForEntity("/bookings/{id}", Booking.class, 1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
