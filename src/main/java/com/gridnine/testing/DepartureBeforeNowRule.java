package com.gridnine.testing;

import java.time.LocalDateTime;

public class DepartureBeforeNowRule implements FlightFilterRule {
    private final LocalDateTime now;

    public DepartureBeforeNowRule() {
        this.now = LocalDateTime.now(); // Фиксируем "сейчас" при создании правила
    }

    // Для тестирования можно передать "now"
    public DepartureBeforeNowRule(LocalDateTime specificNow) {
        this.now = specificNow;
    }

    @Override
    public boolean test(Flight flight) {
        for (Segment segment : flight.getSegments()) {
            if (segment.getDepartureDate().isBefore(now)) {
                return false; // Нашли сегмент, нарушающий правило -> исключаем перелет
            }
        }
        return true; // Все сегменты удовлетворяют правилу
    }
}
