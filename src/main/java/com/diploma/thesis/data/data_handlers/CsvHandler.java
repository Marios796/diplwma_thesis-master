package com.diploma.thesis.data.data_handlers;

import com.diploma.thesis.data.DummyEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//διαβασμα πηγης οταν εχουμε csv
public class CsvHandler {

    private static final String csvFile = "src/main/resources/static/files/data.csv";

    public static List<DummyEntity> readCsvToMap() throws IOException {
        CsvListReader csvReader = new CsvListReader(new InputStreamReader(new FileInputStream(csvFile)), CsvPreference.STANDARD_PREFERENCE);
        List<String> header;

        header = new ArrayList<String>(csvReader.read());

        List<String> rowAsTokens;
        List<Map<String, String>> rows = new ArrayList<>();
        Map<String, String> row;

        while ((rowAsTokens = csvReader.read()) != null) {
            //Create Map for each row in CSV
            row = new HashMap<>();

            for (int i = 0; i < header.size(); i++) {
                row.put(header.get(i).replaceAll("\\s", ""), rowAsTokens.get(i).replaceAll("\\s", ""));
            }

            //add Row map to list of rows
            rows.add(row);
        }
        ObjectMapper mapper = new ObjectMapper();
        List<DummyEntity> list = new ArrayList<>();
        //Iterate
        for (Map<String, String> rowMap : rows) {
            DummyEntity infos = mapper.convertValue(rowMap, DummyEntity.class);
            list.add(infos);
        }

        return list;
    }
}

