package com.company;

import com.company.model.PairEmployees;
import com.company.reader.CSVReader;
import com.company.service.Service;

import java.nio.file.Path;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        Path path = Path.of("sourceData.csv");
        String csvFile = path.toAbsolutePath().toString();
        CSVReader.read(csvFile);

        Service service = new Service();
        service.runPairCombinationAlgorithm();
        service.filterPairCombinationsByProjectId();
        service.calculateDaysWorkedTogether();

        HashMap<PairEmployees, Long> map = service.getMap();

        var longestPair = map.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get();


        System.out.println("\nPair of employees who have worked together on common projects for the longest period of time.");
        System.out.println("EmployeeID #1: " + longestPair.getKey().getEmployeeOne());
        System.out.println("EmployeeID #2: " + longestPair.getKey().getEmployeeTwo());
        System.out.println("Days worked #3: " + longestPair.getValue());
    }
}
