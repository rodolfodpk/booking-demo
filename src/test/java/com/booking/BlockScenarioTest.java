package com.booking;

import com.booking.core.BookingData.Block;
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

import static com.booking.TestData.block1;
import static com.booking.TestData.booking1;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class BlockScenarioTest {

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
    public void whenAddingNewBlock() {
        var response = restTemplate.postForEntity("/blocks", block1, Block.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(3)
    public void whenAddingNewBooking() {
        var response = restTemplate.postForEntity("/bookings", booking1, Booking.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @Order(4)
    public void thenThereIsNoAnyBooking() {
        var response = restTemplate.getForEntity("/bookings/{id}", Booking.class, 1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
