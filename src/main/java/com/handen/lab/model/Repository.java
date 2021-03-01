package com.handen.lab.model;

import com.handen.lab.data.Employee;
import com.handen.lab.data.Record;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Repository {

    private static int id = 1;

    public static int getNewId() {
        id++;
        return id;
    }

    public List<Employee> loadRecords(File file) {
        //TODO
        return new ArrayList<>();
//        LinkedList<Record> records = new LinkedList<>();
//        try(FileReader fileInputStream = new FileReader(file)) {
//            BufferedReader reader = new BufferedReader(fileInputStream);
//            records = reader.lines().map(line -> {
//                String[] array = line.replace("\n", "").split(";");
//                short year = -1;
//                try {
//                    year = Short.parseShort(array[2]);
//                }
//                catch(NumberFormatException e) {
//                    e.printStackTrace();
//                }
//                return new Record(array[0], array[1], year);
//            }).collect(Collectors.toCollection(LinkedList::new));
//        }
//        catch(IOException e) {
//            e.printStackTrace();
//        }
//        return FXCollections.observableList(records);
    }

    public void saveRecordsInDirectory(File directory, ObservableList<Employee> records) {
        //TODO
//        File outputFile = new File(directory, "records.csv");
//        try(FileWriter fileWriter = new FileWriter(outputFile)) {
//            for(Record record : records) {
//                fileWriter.write(record.toString() + "\n");
//            }
//        }
//        catch(IOException e) {
//            e.printStackTrace();
//        }
    }
}
