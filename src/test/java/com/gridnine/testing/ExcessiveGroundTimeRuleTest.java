package com.gridnine.testing;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ExcessiveGroundTimeRuleTest {

    private final ExcessiveGroundTimeRule rule = new ExcessiveGroundTimeRule();

    @Test
    void testSingleSegmentFlight_ShouldPass() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Flight flight = new Flight(List.of(
                new Segment(now, now.plusHours(2))
        ));

        // When
        boolean result = rule.test(flight);

        // Then
        assertTrue(result, "Single segment flight should always pass");
    }

    @Test
    void testTwoSegmentsWithAcceptableGroundTime_ShouldPass() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Flight flight = new Flight(List.of(
                new Segment(now, now.plusHours(2)),
                new Segment(now.plusHours(3), now.plusHours(5))
        ));

        // When
        boolean result = rule.test(flight);

        // Then
        assertTrue(result, "Flight with 1 hour ground time should pass");
    }

    @Test
    void testTwoSegmentsWithExcessiveGroundTime_ShouldFail() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Flight flight = new Flight(List.of(
                new Segment(now, now.plusHours(2)),
                new Segment(now.plusHours(5), now.plusHours(7))
        ));

        // When
        boolean result = rule.test(flight);

        // Then
        assertFalse(result, "Flight with 3 hours ground time should fail");
    }

    @Test
    void testMultipleSegmentsWithTotalGroundTimeExceedingLimit_ShouldFail() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Flight flight = new Flight(Arrays.asList(
                new Segment(now, now.plusHours(1)),
                new Segment(now.plusHours(2), now.plusHours(3)),
                new Segment(now.plusHours(5), now.plusHours(6))
        ));

        // When
        boolean result = rule.test(flight);

        // Then
        assertFalse(result, "Flight with total 3 hours ground time (1+2) should fail");
    }

    @Test
    void testMultipleSegmentsWithTotalGroundTimeWithinLimit_ShouldPass() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Flight flight = new Flight(Arrays.asList(
                new Segment(now, now.plusHours(1)),
                new Segment(now.plusHours(1).plusMinutes(30), now.plusHours(2)),
                new Segment(now.plusHours(3), now.plusHours(4)))
        );

        // When
        boolean result = rule.test(flight);

        // Then
        assertTrue(result, "Flight with total 2 hours ground time (0.5 + 1) should pass");
    }

    @Test
    void testSegmentsWithNegativeGroundTime_ShouldPass() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Flight flight = new Flight(List.of(
                new Segment(now, now.plusHours(5)),
                new Segment(now.plusHours(4), now.plusHours(6)) // Arrival before departure of next
        ));

        // When
        boolean result = rule.test(flight);

        // Then
        assertTrue(result, "Flight with negative ground time should pass (invalid segment but not this rule's concern)");
    }
}