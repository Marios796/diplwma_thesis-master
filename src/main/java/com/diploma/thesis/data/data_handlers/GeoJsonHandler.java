package com.diploma.thesis.data.data_handlers;

import com.diploma.thesis.data.DummyEntity;
import com.diploma.thesis.models.Feature;
import com.diploma.thesis.models.Geometry;
import com.diploma.thesis.models.Properties;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;

public class GeoJsonHandler {

    public static Feature createGeoJSONFeature(DummyEntity locationInfos) throws JsonProcessingException {

        com.diploma.thesis.models.Feature feature = new com.diploma.thesis.models.Feature();
        feature.setType("Feature");
        Geometry geometry = new Geometry();
        geometry.setType("Point");
        List<Double> yo = new ArrayList<>();
        yo.add(locationInfos.getY());
        yo.add(locationInfos.getX());
        geometry.setCoordinates(yo);
        feature.setGeometry(geometry);
        Properties properties = new Properties();
        properties.setName(locationInfos.getText());   //allazoume poio property 8a emfanizetai sto popup
        feature.setProperties(properties);
        return feature;
    }
}
