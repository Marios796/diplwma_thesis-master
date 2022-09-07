package com.diploma.thesis.data;

import com.diploma.thesis.data.data_handlers.GeoJsonHandler;
import com.diploma.thesis.data.data_handlers.JsonHandler;
import com.diploma.thesis.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//αναπτυξη των queries που εχουν δηλωθει απ'τον διαχειριστη στο admin.json
public class Queries {
    private String currentQ;
    private Map<String, String> currentQInputs;
    private final List<String> queriesNames;
    private List<String> inputResults;

    public Queries() {
        queriesNames = JsonHandler.getQueriesNames();
        inputResults = new ArrayList<>();
    }

    public static String keywordQuery(ArrayList<String> inputs,ArrayList<DummyEntity> data) throws JsonProcessingException {
        List<DummyEntity> list;
        List<Feature> features = new ArrayList<>();
        list = data;
        ObjectMapper mapper = new ObjectMapper();
        FeatureCollection featureCollection = new FeatureCollection();
        JSONArray jsonArray = new JSONArray();
         for (DummyEntity locationInfos : list) {
            if (locationInfos.getKeyword().toLowerCase().contains((inputs.get(0)).toLowerCase())) {  //case insensitive comparison
                Feature feature = GeoJsonHandler.createGeoJSONFeature(locationInfos);
                String jsonInString = mapper.writeValueAsString(feature);
                features.add(feature);
            }
        }
        featureCollection.setFeatures(features);
        return mapper.writeValueAsString(featureCollection);  //string gia na diavazetai eukola sto frontend
    }

    public static String scoreQuery(ArrayList<String> inputs, ArrayList<DummyEntity> data) throws JsonProcessingException {
        List<DummyEntity> list =data;
        List<Feature> features = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        FeatureCollection featureCollection = new FeatureCollection();
        JSONArray jsonArray = new JSONArray();
        for (DummyEntity locationInfos : list) {
            if (locationInfos.getScore() >= Integer.parseInt(inputs.get(0))) {
                Feature feature = GeoJsonHandler.createGeoJSONFeature(locationInfos);
                String jsonInString = mapper.writeValueAsString(feature);
                features.add(feature);
            }
        }
        featureCollection.setFeatures(features);
        return mapper.writeValueAsString(featureCollection);
    }

    public void setCurrentQInputs(JSONObject jsonObject) {
        currentQInputs = new HashMap<>();
        for (Object o : jsonObject.keySet()) {
            String key = (String) o;
            String value = (String) jsonObject.get(key);
            currentQInputs.put(key, value);
        }

    }

    public String getCurrentQ() {
        return currentQ;
    }

    public void setCurrentQ(String currentQ) {
        this.currentQ = currentQ;
    }

    public List<String> getQueriesNames() {
        return queriesNames;
    }

    public Map<String, String> getCurrentQInputs() {
        return currentQInputs;
    }

    public List<String> getInputResults() {
        return inputResults;
    }

    public void setInputResults(List<String> inputResults) {
        this.inputResults = inputResults;
    }
}
