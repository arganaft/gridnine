package com.gridnine.testing;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrivalBeforeDepartureRuleTest {
    private final ArrivalBeforeDepartureRule rule = new ArrivalBeforeDepartureRule();

    @Test
    void testValidSingleSegment_ShouldPass() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Flight flight = new Flight(List.of(
                new Segment(now, now.plusHours(2))
        ));

        // When
        boolean result = rule.test(flight);

        // Then
        assertTrue(result, "Valid single segment should pass");
    }

    @Test
    void testValidMultiSegment_ShouldPass() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Flight flight = new Flight(List.of(
                new Segment(now, now.plusHours(1)),
                new Segment(now.plusHours(2), now.plusHours(3))
        ));

        // When
        boolean result = rule.test(flight);

        // Then
        assertTrue(result, "Valid multi-segment flight should pass");
    }

    @Test
    void testArrivalBeforeDeparture_ShouldFail() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Flight flight = new Flight(List.of(
                new Segment(now, now.minusHours(1))) // Прилет раньше вылета
        );

        // When
        boolean result = rule.test(flight);

        // Then
        assertFalse(result, "Segment with arrival before departure should fail");
    }

    @Test
    void testArrivalEqualsDeparture_ShouldPass() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Flight flight = new Flight(List.of(
                new Segment(now, now)) // Прилет равен вылету
        );

        // When
        boolean result = rule.test(flight);

        // Then
        assertTrue(result, "Segment with arrival equal to departure should pass");
    }

    @Test
    void testOneInvalidSegmentInMultiSegmentFlight_ShouldFail() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Flight flight = new Flight(List.of(
                new Segment(now, now.plusHours(1)), // Valid
                new Segment(now.plusHours(2), now.plusHours(1)), // Invalid
                new Segment(now.plusHours(3), now.plusHours(4))) // Valid
        );

        // When
        boolean result = rule.test(flight);

        // Then
        assertFalse(result, "Flight with one invalid segment should fail");
    }

    @Test
    void testAllSegmentsInvalid_ShouldFail() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Flight flight = new Flight(List.of(
                new Segment(now.plusHours(1), now),
                new Segment(now.plusHours(3), now.plusHours(2)))
        );

        // When
        boolean result = rule.test(flight);

        // Then
        assertFalse(result, "Flight with all invalid segments should fail");
    }
}