package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

public class CSVUtilTest {

    @Test
    void converterData(){
        List<Correo> list = CsvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void stream_filtrarJugadoresMayoresA35(){
        List<Correo> list = CsvUtilFile.getPlayers();
        Map<String, List<Correo>> listFilter = list.parallelStream()
                .filter(correo -> correo.age >= 35)
                .map(correo -> {
                    correo.name = correo.name.toUpperCase(Locale.ROOT);
                    return correo;
                })
                .flatMap(correoA -> list.parallelStream()
                        .filter(correoB -> correoA.club.equals(correoB.club))
                )
                .distinct()
                .collect(Collectors.groupingBy(Correo::getClub));

        assert listFilter.size() == 322;
    }


    @Test
    void reactive_filtrarJugadoresMayoresA35(){
        List<Correo> list = CsvUtilFile.getPlayers();
        Flux<Correo> listFlux = Flux.fromStream(list.parallelStream()).cache();
        Mono<Map<String, Collection<Correo>>> listFilter = listFlux
                .filter(correo -> correo.age >= 35)
                .map(correo -> {
                    correo.name = correo.name.toUpperCase(Locale.ROOT);
                    return correo;
                })
                .buffer(100)
                .flatMap(playerA -> listFlux
                         .filter(correoB -> playerA.stream()
                                 .anyMatch(a ->  a.club.equals(correoB.club)))
                )
                .distinct()
                .collectMultimap(Correo::getClub);

        assert listFilter.block().size() == 322;
    }

    // Test para encontrar jugadores mayores de 34 años pertenecientes a cierto club
    @Test
    void reactive_filtrarJugadoresMayoresA34(){
        List<Correo> list = CsvUtilFile.getPlayers();
        Flux<Correo> listFlux = Flux.fromStream(list.parallelStream()).cache();

        Mono<Map<String, Collection<Correo>>> listFilter = listFlux
                .filter(correo -> correo.club.equals("Athletic Club de Bilbao") && correo.getAge() >= 34)
                .distinct()
                .collectMultimap(Correo::getClub);

        System.out.println(listFilter.block().size());

        listFilter.block().forEach((equipo, players) -> {
            players.forEach(correo -> {
                System.out.println("Nombre: " + correo.getName() + "\nEdad: " + correo.getAge() + " años" +"\nClub: " + correo.getClub());
                assert correo.club.equals("Athletic Club de Bilbao");

                Assertions.assertEquals(37, correo.getAge());
            });
        });

    }

    //
    @Test
    void reactive_filtrarPorNacionYFiltrarRanked() {
        List<Correo> list = CsvUtilFile.getPlayers();
        Flux<Correo> listFlux = Flux.fromStream(list.parallelStream()).cache();
        Mono<Map<String, Collection<Correo>>> listFilter = listFlux
                .buffer(100)
                .flatMap(player1 -> listFlux
                        .filter(correo2 -> player1.stream()
                                .anyMatch(a -> a.getNational().equals(correo2.getNational())))
                ).distinct()
                .sort((k, correo) -> correo.getWinners())
                .collectMultimap(Correo::getNational);

        System.out.println("Filtro Por naciones: ");
        System.out.println(listFilter.block().size());
        listFilter.block().forEach((pais, players) -> {
            System.out.println("Pais: " + pais + "\n{");
            players.forEach(correo -> {
                System.out.println("- Nombre: " + correo.getName() +
                        "\n- victorias: " + correo.getWinners() + "\n- Club: " + correo.getClub());
            });
            System.out.println("}");
        });
    }








}
