package com.diploma.thesis.data.data_handlers;

import com.diploma.thesis.data.DummyEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlHandler {

    private static final ArrayList<Element> dataList = new ArrayList<>();
    private static final String csvFile = "src/main/resources/static/files/data.xml";

    public static List<DummyEntity> readDataFromXml() {
        Map<String, String> map = new HashMap<String, String>();
        List<DummyEntity> list = new ArrayList<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(csvFile);
            NodeList nodeList = doc.getChildNodes();
            ObjectMapper mapper = new ObjectMapper();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node textChild = nodeList.item(i);
                NodeList childNodes = textChild.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node grantChild = childNodes.item(j);
                    NodeList grantChildNodes = grantChild.getChildNodes();
                    for (int k = 1; k < grantChildNodes.getLength(); k=k+2) {

                        map.put(grantChildNodes.item(k).getNodeName(), grantChildNodes.item(k).getTextContent());
                        System.out.println(map);
                        DummyEntity infos = mapper.convertValue(map, DummyEntity.class);
                        if(k>=11) {
                            list.add(infos);
//                            for (int l = 0; l<list.size();l++){
//                                if(list.get(l).getText().equals(list.get(list.size()-1))){
//                                    list.remove(list.size()-1);
//                                    break;
//                                }
//                            }
//                        }
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
