package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

public class CSVUtilTest {

    @Test
    void stream_filtrarCorreosDisticnt(){
        List<Correo> list = CsvUtilFile.getPlayers();

        Collection<Correo> collection = new ArrayList(new HashSet(list));

        Set<String> areas = new HashSet<>();
        for(final Correo x: list) {
            areas.add(x.getEmail());
        }

        List<String> distinctElements = areas.stream()
                .distinct()
                .collect(Collectors.toList());

        System.out.println("Quitar repetidos");
        distinctElements.forEach(
                (correo) ->{ System.out.println(correo);}
        );

        System.out.println(distinctElements.size());
        assert distinctElements.size() == 9;

    }


    @Test
    void stream_filtroPorDominio(){

        List<Correo> list = CsvUtilFile.getPlayers();
        Flux<Correo> listFlux = Flux.fromStream(list.parallelStream()).cache();
        Mono<Map<Integer, Collection<Correo>>> listFilter = listFlux
                .filter(correo -> correo.email.contains("@gmail"))
                .distinct()
                .collectMultimap(Correo::getId);


        System.out.println("Filtrar por dominio @gmail");
        listFilter.block().forEach((id, correos) ->
        {
            System.out.println("ID: " + id);
            correos.forEach(correo ->
            {
                System.out.println("Correo: " + correo.getEmail());
            });
        } );

    }

    @Test
    void stream_saberTamaño(){
        List<Correo> list = CsvUtilFile.getPlayers();

        Integer mapCorreos = list.stream().map(correo -> correo).collect(Collectors.toList()).size();

        System.out.println(mapCorreos);
    }

    @Test
    void stream_saberCorreo(){
        List<Correo> list = CsvUtilFile.getPlayers();

        List<String> list2 = list.stream().map(correo -> correo.getEmail().split("@", 0)[1]).collect(Collectors.toList());


        for (String dom: list2
             ) {
            System.out.println(dom);
        }

        int occurrencesYahoo = Collections.frequency(list2, "yahoo.com");
        System.out.println("El correo @yahoo.com se repite " + occurrencesYahoo);

        int occurrencesGoogle = Collections.frequency(list2, "gmail.com");
        System.out.println("El correo @gmail.com se repite " + occurrencesGoogle);


    }


    @Test
    void stream_contieneArrobaDominio(){
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";


        List<Correo> list = CsvUtilFile.getPlayers();

        List<String> listMap = list.stream().map(correo -> correo.getEmail()).filter(correo -> correo.matches(regexPattern))
                .collect(Collectors.toList());


        Flux<Correo> listFlux = Flux.fromStream(list.parallelStream()).cache();
        Mono<Map<Integer, Collection<Correo>>> listFilter = listFlux
                .filter(correo -> correo.email.contains(regexPattern))
                .collectMultimap(Correo::getId);




        listMap.forEach((correos) ->
        {
            System.out.println("Correo: " + correos);
        } );
    }
}
