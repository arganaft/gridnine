package com.gridnine.testing;

public class ArrivalBeforeDepartureRule implements FlightFilterRule {
    @Override
    public boolean test(Flight flight) {
        for (Segment segment : flight.getSegments()) {
            if (segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                return false; // Нашли некорректный сегмент -> исключаем перелет
            }
        }
        return true; // Все сегменты корректны
    }
}
