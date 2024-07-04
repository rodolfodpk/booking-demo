package com.booking;

import com.booking.core.BookingData.Booking;
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
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class BookingNotFoundScenarioTest {

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
    public void whenUpdatingMissingBooking() {
        restTemplate.put("/bookings/{id}", booking1, 1);
    }

    @Test
    @Order(3)
    public void whenDeletingMissingBooking() {
        restTemplate.delete("/bookings/{id}", 1);
    }

    @Test
    @Order(4)
    public void thenThereArentAnyBooking() {
        var response = restTemplate.getForEntity("/bookings/{id}", Booking.class, 1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
