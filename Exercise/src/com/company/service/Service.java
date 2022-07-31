package com.company.service;

import com.company.model.PairEmployees;
import com.company.model.Record;
import com.company.source.Source;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Service {

    private List<List<Record>> records;
    private List<List<Record>> filteredRecords;
    private HashMap<PairEmployees, Long> map;

    public Service() {
        this.records = new ArrayList<List<Record>>();
        this.filteredRecords = new ArrayList<List<Record>>();
        this.map = new HashMap<PairEmployees, Long>();
    }

    public List<List<Record>> getRecords() {
        return records;
    }

    public List<List<Record>> getFilteredRecords() {
        return filteredRecords;
    }

    public HashMap<PairEmployees, Long> getMap() {
        return map;
    }

    public List<Record> cloneArray (List<Record> tempArr) {
        List<Record> clonedList = new ArrayList<>(tempArr);
        return clonedList;
    }

    public void runPairCombinationAlgorithm() {
        int r = 2;
        int n = Source.getRecords().size();
        Record []arr = new Record[n];
        Record []data = new Record[r];

        findAllPairCombinations(Source.getRecords().toArray(arr), n, r, 0, data, 0);
    }

    private void findAllPairCombinations(Record[] arr, int n, int r, int index,  Record[] data, int i){
        if (index == r) {

            List<Record> tempArr = new ArrayList<>();

            for (int j = 0; j < r; j++) {
                tempArr.add(data[j]);
            }

            this.records.add(cloneArray(tempArr));

            return;
        }

        if (i >= n)
            return;

        data[index] = arr[i];
        findAllPairCombinations(arr, n, r, index + 1, data, i + 1);
        findAllPairCombinations(arr, n, r, index, data, i + 1);
    }

    public void filterPairCombinationsByProjectId() {
        this.filteredRecords = records.stream()
                .filter(record ->
                        record.get(0).getProjectId() == record.get(1).getProjectId() &&
                                record.get(0).getEmployeeId() != record.get(1).getEmployeeId())
                                .collect(Collectors.toList());
    }

    public void calculateDaysWorkedTogether() {
        for(List<Record> row : filteredRecords) {

            if(!haveOverlap(row.get(0), row.get(1)))
                continue;

            long daysWorkedTogether = calculateOverlap(row.get(0), row.get(1));

            PairEmployees pairOfEmployees = new PairEmployees(row.get(0).getEmployeeId(), row.get(1).getEmployeeId());

            if(map.containsKey(pairOfEmployees)) {
                map.put(pairOfEmployees, map.get(pairOfEmployees) + daysWorkedTogether);
                return;
            }

            map.put(pairOfEmployees, daysWorkedTogether);
        }
    }

    private boolean haveOverlap(Record employeeOne, Record employeeTwo) {
        return (
                (employeeOne.getDateFrom().isBefore(employeeTwo.getDateTo()) ||
                        employeeOne.getDateFrom().isEqual(employeeTwo.getDateTo()))
                        &&
                (employeeTwo.getDateFrom().isBefore(employeeOne.getDateTo()) ||
                        employeeTwo.getDateFrom().isEqual(employeeOne.getDateTo()))
        );
    }

    private long calculateOverlap(Record employeeOne, Record employeeTwo) {
        LocalDate start =
                employeeOne.getDateFrom().isBefore(employeeTwo.getDateFrom()) ?
                        employeeTwo.getDateFrom() : employeeOne.getDateFrom();

        LocalDate end =
                employeeOne.getDateTo().isBefore(employeeTwo.getDateTo()) ?
                        employeeOne.getDateTo() : employeeTwo.getDateTo();

        return Math.abs(ChronoUnit.DAYS.between(start, end));
    }
}
