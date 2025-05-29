package com.gridnine.testing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для фильтрации списка перелетов на основе заданных правил.
 */
public class FlightFilterService {

    /**
     * Фильтрует список перелетов, оставляя только те, которые соответствуют правилу.
     *
     * @param flights Список перелетов для фильтрации.
     * @param rule    Правило, которое должно быть применено.
     * @return Новый список перелетов, удовлетворяющих правилу.
     */
    public List<Flight> filterFlights(List<Flight> flights, FlightFilterRule rule) {
        if (flights == null || rule == null) {
            // Можно бросить IllegalArgumentException или вернуть пустой/оригинальный список
            // в зависимости от требований к обработке ошибок.
            // Для простоты вернем пустой список, если flights is null.
            return flights == null ? new ArrayList<>() : new ArrayList<>(flights);
        }
        return flights.stream()
                .filter(rule::test) // Используем ссылку на метод test интерфейса
                .collect(Collectors.toList());
    }

    /**
     * Альтернативный метод, если нужно применить несколько правил последовательно
     * (оставляя перелеты, которые удовлетворяют ВСЕМ правилам).
     * В данном задании это не требуется напрямую, но полезно для расширяемости.
     */
    public List<Flight> filterFlights(List<Flight> flights, List<FlightFilterRule> rules) {
        if (flights == null || rules == null || rules.isEmpty()) {
            return flights == null ? new ArrayList<>() : new ArrayList<>(flights);
        }
        List<Flight> filteredFlights = new ArrayList<>(flights);
        for (FlightFilterRule rule : rules) {
            filteredFlights = filteredFlights.stream()
                    .filter(rule::test)
                    .collect(Collectors.toList());
        }
        return filteredFlights;
    }
}
