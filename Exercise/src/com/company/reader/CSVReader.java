package com.company.reader;

import com.company.model.Record;
import com.company.source.Source;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class CSVReader {
    private static DateTimeFormatter dateTimeFormatter;
    private static final String delimiter = ",";

    static {
        dateTimeFormatter = new DateTimeFormatterBuilder()
                // CaseInsensitive - SEP = Sep = sep
                .parseCaseInsensitive()
                .appendPattern("[yyyy-MM-dd]")
                .appendPattern("[yyyy-MM-d]")
                .appendPattern("[yyyy-M-dd]")
                .appendPattern("[yyyy-M-d]")
                .appendPattern("[dd/M/yyyy]")
                .appendPattern("[d/M/yyyy]")
                .appendPattern("[dd/MM/yyyy]")
                .appendPattern("[d/MM/yyyy]")
                .appendPattern("[yyyy/M/dd]")
                .appendPattern("[yyyy/M/d]")
                .appendPattern("[yyyy/MM/dd]")
                .appendPattern("[yyyy/MM/d]")
                .appendPattern("[dd-M-yyyy]")
                .appendPattern("[d-M-yyyy]")
                .appendPattern("[dd-MM-yyyy]")
                .appendPattern("[d-MM-yyyy]")
                .appendPattern("[dd-MMM-yyyy]")
                .appendPattern("[d-MMM-yyyy]")
                .appendPattern("[dd/MMM/yyyy]")
                .appendPattern("[d/MMM/yyyy]")
                .appendPattern("[MMMM dd, yyyy]")
                .toFormatter(Locale.ENGLISH);
    }

    public static void read(String csvFile) {
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            String[] tempArr;
            while ((line = br.readLine()) != null) {
                tempArr = line.split(delimiter);
                trimStringArrayItems(tempArr);
                convertToRecord(tempArr);
            }
            br.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void trimStringArrayItems(String[] tempArr) {
        for (int i = 0; i < tempArr.length; i++)
            tempArr[i] = tempArr[i].trim();
    }

    private static void convertToRecord(String[] tempArr) {

        final LocalDate dateFrom = LocalDate.parse(tempArr[2], dateTimeFormatter);

        final LocalDate dateTo = tempArr[3]
                .equalsIgnoreCase("null")
                ? LocalDate.now() : LocalDate.parse(tempArr[3], dateTimeFormatter);

        Source.addRecord(new Record(
                Long.parseLong(tempArr[0]),
                Long.parseLong(tempArr[1]),
                dateFrom,
                dateTo));

    }
}