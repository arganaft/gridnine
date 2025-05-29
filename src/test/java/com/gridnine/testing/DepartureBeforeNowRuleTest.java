package com.gridnine.testing;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartureBeforeNowRuleTest {
    @Test
    void testAllSegmentsAfterNow_ShouldPass() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        DepartureBeforeNowRule rule = new DepartureBeforeNowRule(now);
        Flight flight = new Flight(List.of(
                new Segment(now.plusHours(1), now.plusHours(2)),
                new Segment(now.plusHours(3), now.plusHours(4))
        ));

        // When
        boolean result = rule.test(flight);

        // Then
        assertTrue(result, "All segments depart in the future - should pass");
    }

    @Test
    void testOneSegmentBeforeNow_ShouldFail() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        DepartureBeforeNowRule rule = new DepartureBeforeNowRule(now);
        Flight flight = new Flight(List.of(
                new Segment(now.minusHours(1), now.plusHours(1)),
                new Segment(now.plusHours(2), now.plusHours(3))
        ));

        // When
        boolean result = rule.test(flight);

        // Then
        assertFalse(result, "Flight with one segment departing in the past - should fail");
    }

    @Test
    void testAllSegmentsBeforeNow_ShouldFail() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        DepartureBeforeNowRule rule = new DepartureBeforeNowRule(now);
        Flight flight = new Flight(List.of(
                new Segment(now.minusHours(3), now.minusHours(2)),
                new Segment(now.minusHours(1), now.plusMinutes(30))
        ));

        // When
        boolean result = rule.test(flight);

        // Then
        assertFalse(result, "All segments depart in the past - should fail");
    }

    @Test
    void testSegmentDepartingExactlyNow_ShouldPass() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        DepartureBeforeNowRule rule = new DepartureBeforeNowRule(now);
        Flight flight = new Flight(List.of(
                new Segment(now, now.plusHours(1)))
        );

        // When
        boolean result = rule.test(flight);

        // Then
        assertTrue(result, "Segment departing exactly now - should pass");
    }

    @Test
    void testSingleSegmentFlightAfterNow_ShouldPass() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        DepartureBeforeNowRule rule = new DepartureBeforeNowRule(now);
        Flight flight = new Flight(List.of(
                new Segment(now.plusMinutes(1), now.plusHours(1)))
        );

        // When
        boolean result = rule.test(flight);

        // Then
        assertTrue(result, "Single segment flight in the future - should pass");
    }

    @Test
    void testSingleSegmentFlightBeforeNow_ShouldFail() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        DepartureBeforeNowRule rule = new DepartureBeforeNowRule(now);
        Flight flight = new Flight(List.of(
                new Segment(now.minusHours(1), now.plusHours(1)))
        );

        // When
        boolean result = rule.test(flight);

        // Then
        assertFalse(result, "Single segment flight in the past - should fail");
    }
}