package com.diploma.thesis.data.data_handlers;

import com.diploma.thesis.data.DummyEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.*;
import java.util.*;
//διαβασμα πηγης οταν εχουμε json

public class JsonHandler {
    private static final String adminsJson = "src/main/resources/static/files/admin.json";
    private static final String dataJson = "src/main/resources/static/files/data.json";

    public static List<String> getQueriesNames() {
        List<String> queries = new ArrayList<>();
        for (JSONObject jsonObject : (Iterable<JSONObject>) readAdminsJson("queries")) {
            queries.add((String) jsonObject.get("name"));
        }
        return queries;
    }


    public static JSONObject setCurrentQInputs(String currentQInputs) {
        List<String> queries = new ArrayList<>();
        for (JSONObject it : (Iterable<JSONObject>) readAdminsJson("queries")) {
            if (it.get("name").equals(currentQInputs)) {
                return (JSONObject) it.get("parameters");

            }
        }
        return null;
    }

    public static String getInputType() {
        for (JSONObject it : (Iterable<JSONObject>) readAdminsJson("data")) {
            return it.get("source").toString();
        }
        return "";
    }

    private static JSONArray readAdminsJson(String get) {
        JSONParser parser = new JSONParser();
        Object obj;
        JSONArray queriesList = null;
        try {
            obj = parser.parse(new FileReader(adminsJson));
            JSONObject jsonObject = (JSONObject) obj;
            queriesList = (JSONArray) jsonObject.get(get);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return queriesList;
    }


    public static ArrayList<DummyEntity> readDataJSON() throws IOException, ParseException, JSONException {
        JSONParser jsonParser = new JSONParser();
        ArrayList<DummyEntity> dataList = new ArrayList<>();
        //Parsing the contents of the JSON file
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(dataJson));
        List<Map<String, Object>> rows = new ArrayList<>();

        for (int i = 0, size = jsonArray.size(); i < size; i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            Map<String, Object> row = toMap(jsonObject);
            rows.add(row);
        }
        ObjectMapper mapper = new ObjectMapper();
        for (Map<String, Object> rowMap : rows) {
            DummyEntity infos = mapper.convertValue(rowMap, DummyEntity.class);
            dataList.add(infos);
        }
        return dataList;
    }

    public static Map<String, Object> toMap(JSONObject jsonobj) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();
        Iterator<String> keys = jsonobj.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = jsonobj.get(key);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

}
