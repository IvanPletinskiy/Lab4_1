package com.handen.lab.model;

import com.handen.lab.data.Employee;
import com.handen.lab.data.developer.Developer;
import com.handen.lab.data.developer.MobileDeveloper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RepositoryProxy {
    private static RepositoryProxy instance = null;

    private ObservableList<Employee> items = FXCollections.observableArrayList(new MobileDeveloper(0, "Ivan", "Pletinskiy", 100500));

    private RepositoryProxy() {

    }


    public Developer getMentorBySurname(String surname) {
        for(Employee employee :items) {
            if(employee.getSurname().equals(surname)) {
                return (Developer) employee;
            }
        }
        return null;
    }

    public static RepositoryProxy getInstance() {
        if(instance == null) {
            instance = new RepositoryProxy();
        }
        return instance;
    }

    public ObservableList<Employee> getItems() {
        return items;
    }

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
