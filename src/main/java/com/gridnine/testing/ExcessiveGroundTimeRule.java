package com.gridnine.testing;

import java.time.Duration;
import java.util.List;

public class ExcessiveGroundTimeRule implements FlightFilterRule {
    private static final long MAX_GROUND_TIME_HOURS = 2;

    @Override
    public boolean test(Flight flight) {
        List<Segment> segments = flight.getSegments();
        if (segments.size() < 2) {
            return true; // Нет пересадок, значит нет времени на земле
        }

        long totalGroundTimeSeconds = 0;
        for (int i = 0; i < segments.size() - 1; i++) {
            Segment currentSegment = segments.get(i);
            Segment nextSegment = segments.get(i + 1);
            Duration groundTime = Duration.between(currentSegment.getArrivalDate(), nextSegment.getDepartureDate());
            totalGroundTimeSeconds += groundTime.getSeconds();
        }

        return Duration.ofSeconds(totalGroundTimeSeconds).toHours() <= MAX_GROUND_TIME_HOURS;
    }
}
