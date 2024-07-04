package com.booking;

import com.booking.core.BookingData.Block;
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
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class BlockBasicScenarioTest {

    Block updatedBlock1 = new Block(1, block1.property(), block1.start(), block1.end().plusDays(1));
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void whenAddingNewBlock() {
        var response = restTemplate.postForEntity("/blocks", block1, Block.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(2)
    public void whenUpdatingBlock() {
        restTemplate.put("/blocks/{id}", updatedBlock1, 1);
    }

    @Test
    @Order(3)
    public void whenDeletingBlock() {
        restTemplate.delete("/blocks/{id}", 1);
    }

    @Test
    @Order(4)
    public void whenUpdatingMissingBlock() {
        restTemplate.put("/blocks/{id}", updatedBlock1, 0);
    }

    @Test
    @Order(5)
    public void whenDeletingMissingBlock() {
        restTemplate.delete("/blocks/{id}", 0);
    }
}
