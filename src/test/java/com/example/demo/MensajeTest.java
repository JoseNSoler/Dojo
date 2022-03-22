package com.example.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MensajeTest {

    @Test
    void stream_chat(){
        List<String> malasPalabras = new ArrayList<>();
        malasPalabras.add("mk");
        malasPalabras.add("hp");
        malasPalabras.add("gono");
        malasPalabras.add("fuck");
        malasPalabras.add("shit");
        malasPalabras.add("puta");
        malasPalabras.add("sapoperro");
        malasPalabras.add("asshole");
        malasPalabras.add("hpta");
        malasPalabras.add("mka");

//        List<Mensaje> list = CsvUtilFile.getPlayers();
//        Flux<Mensaje> listFlux = Flux.fromStream(list.parallelStream()).cache();
//        Mono<List<String>> listFilter = listFlux
//                .map(mensaje -> mensaje.getMsg().split(" ",0))
//                .filter(palabra ->)
//                .distinct()
//                .collect(Collectors.toList());


        listFilter.block().forEach((id, correos) ->
        {
            System.out.println("ID: " + id);
            correos.forEach(correo ->
            {
                System.out.println("Correo: " + correo.getEmail());
            });
        } );
    }

}