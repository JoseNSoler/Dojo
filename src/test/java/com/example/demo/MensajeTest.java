package com.example.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
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

        String palabra = "Esta es una mala palabra soo";
        String regexx = "^.*(Foo|man|choo).*$";

        System.out.println(palabra.matches(regexx));

        List<Mensaje> list = CsvUtilFileChat.getMensajes();
        Flux<Mensaje> listFlux = Flux.fromStream(list.parallelStream()).cache();
        Mono<List<String>> listFilter = listFlux
                .map(mensaje -> mensaje.getMsg().replace("hp", "*****"))
                .distinct()
                .collect(Collectors.toList());

//        if (tex.value.split("palabra")) { tex.value = tex.value.replace("palabra", "******");


            listFilter.block().forEach((id) ->
        {
            System.out.println("ID: " + id);

        } );
    }

}