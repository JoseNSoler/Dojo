package com.example.demo;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvUtilFileChat {
    private CsvUtilFileChat(){}

    public static List<Mensaje> getMensajes(){
        var uri =  CsvUtilFile.class.getClassLoader().getResource("dataChat.csv");
        List<Mensaje> list = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(uri.getFile()))) {
            List<String[]> registers = reader.readAll();
            registers.forEach(strings -> list.add(new Mensaje(
                    Integer.parseInt(strings[0].trim()),
                    strings[1]
            )));

            return list;

        } catch (IOException | CsvException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}