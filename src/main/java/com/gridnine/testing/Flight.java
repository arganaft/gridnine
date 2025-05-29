package com.gridnine.testing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Bean that represents a flight.
 */
class Flight {
    private final List<Segment> segments;

    Flight(final List<Segment> segs) {
        segments = new ArrayList<>(Objects.requireNonNull(segs, "Segments list cannot be null"));
        if (segments.isEmpty()) {
            throw new IllegalArgumentException("Flight must contain at least one segment.");
        }
    }

    List<Segment> getSegments() {
        return new ArrayList<>(segments);
    }

    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}
