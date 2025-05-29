package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> allFlights = FlightBuilder.createFlights();
        FlightFilterService filterService = new FlightFilterService();

        System.out.println("Исходный набор перелетов:");
        printFlights(allFlights);

        System.out.println("\n1. Перелеты ПОСЛЕ текущего момента времени (исключены вылеты до текущего момента):");
        FlightFilterRule rule1 = new DepartureBeforeNowRule(); // Это правило возвращает true, если НЕТ вылетов в прошлом
        List<Flight> filteredByRule1 = filterService.filterFlights(allFlights, rule1);
        printFlights(filteredByRule1);

        System.out.println("\n2. Перелеты, где дата прилёта ПОСЛЕ даты вылета для всех сегментов (исключены некорректные сегменты):");
        FlightFilterRule rule2 = new ArrivalBeforeDepartureRule(); // Это правило возвращает true, если НЕТ некорректных сегментов
        List<Flight> filteredByRule2 = filterService.filterFlights(allFlights, rule2);
        printFlights(filteredByRule2);

        System.out.println("\n3. Перелеты, где общее время на земле НЕ ПРЕВЫШАЕТ два часа (исключены те, где превышает):");
        FlightFilterRule rule3 = new ExcessiveGroundTimeRule(); // Это правило возвращает true, если время на земле <= 2 часов
        List<Flight> filteredByRule3 = filterService.filterFlights(allFlights, rule3);
        printFlights(filteredByRule3);
    }

    private static void printFlights(List<Flight> flights) {
        if (flights.isEmpty()) {
            System.out.println("Нет перелетов, соответствующих критериям.");
            return;
        }
        flights.forEach(System.out::println);
    }
}
