package com.handen.lab.model;

import com.handen.lab.data.Record;

import java.io.File;

import javafx.collections.ObservableList;

public interface BaseModel {
    ObservableList<Record> loadRecords(File file);
    void saveRecords(File file);
}