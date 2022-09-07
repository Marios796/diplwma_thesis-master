package com.diploma.thesis.controllers;

import com.diploma.thesis.data.Queries;
import com.diploma.thesis.data.data_handlers.CsvHandler;
import com.diploma.thesis.data.data_handlers.DbHandler;
import com.diploma.thesis.data.data_handlers.JsonHandler;
import com.diploma.thesis.data.DummyEntity;
import com.diploma.thesis.data.database.ModelRepo;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static com.diploma.thesis.data.data_handlers.JsonHandler.*;
import static com.diploma.thesis.data.data_handlers.XmlHandler.readDataFromXml;

//o controller συνδεει το backend με το frontend
@Controller
public class MapController {


    @Autowired
    public ModelRepo repo;
    private static String curQ;
    List<DummyEntity> data = null;


    @GetMapping("/")
    public String index(Model model) throws IOException {
        Queries queries = new Queries();
        model.addAttribute("queriesNames", queries.getQueriesNames());
        model.addAttribute("queries", queries);
        return "index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchInit(@ModelAttribute(value = "queries") Queries queries, Model model) {
        queries.setCurrentQInputs(Objects.requireNonNull(JsonHandler.setCurrentQInputs(queries.getCurrentQ())));
        System.out.println(queries.getCurrentQ());
        curQ = queries.getCurrentQ();
        model.addAttribute("infos", queries.getCurrentQInputs());
        model.addAttribute("queries", queries);
        return "search";
    }

    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public String search(@ModelAttribute(value = "queries") Queries queries, Model model) {
        try {
            readDataFromCurrentDataSource();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Class<?> clazz = Class.forName("com.diploma.thesis.data.Queries");
            Method method = clazz.getMethod(getCurQ(), ArrayList.class, ArrayList.class);
            String geoJSON = (String) method.invoke(null, queries.getInputResults(), data);
            model.addAttribute("loc", geoJSON);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "result";
    }

    private void readDataFromCurrentDataSource() throws IOException {
        if (getInputType().equals(".csv")) {
            data = CsvHandler.readCsvToMap();
        } else if (getInputType().equals(".xml")) {
            data=readDataFromXml();
        } else if (getInputType().equals(".json")) {
            try {
                data = readDataJSON();
            } catch (ParseException | JSONException e) {

            }
        } else if (getInputType().equals("db")) {
            dataToDB();
            data = DbHandler.getDataFromDB(this.getAllLocations());
        }

    }

    public static String getCurQ() {
        return curQ;
    }

    public Iterable<DummyEntity> getAllLocations() { return repo.findAll(); }  //repo.findall epistrefei oli tin vasi

    public void dataToDB() throws IOException {
        List<DummyEntity> list = CsvHandler.readCsvToMap();
        repo.deleteAll();
        for (DummyEntity rowMap : list) {
            System.out.println("Data to db: " + rowMap.getX() + " " + rowMap.getY()+" !");
            DummyEntity infos2 = new DummyEntity();
            //infos2.setDescription(rowMap.getDescription());
            infos2.setX(rowMap.getX());
            infos2.setY(rowMap.getY());
            infos2.setScore(rowMap.getScore());
            infos2.setKeyword(rowMap.getKeyword());
            repo.save(infos2);  //apo8ikeuei stin vasi
        }
    }

}

