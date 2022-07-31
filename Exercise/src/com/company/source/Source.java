package com.company.source;

import com.company.model.Record;

import java.util.ArrayList;
import java.util.List;

public class Source {

    private static List<Record> records;

    static {
        records = new ArrayList<>();
    }

    public static void addRecord(Record record) {
        records.add(record);
    }

    public static List<Record> getRecords() {
        return records;
    }
}
