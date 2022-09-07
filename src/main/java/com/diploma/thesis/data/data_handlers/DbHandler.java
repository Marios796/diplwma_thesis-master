package com.diploma.thesis.data.data_handlers;

import com.diploma.thesis.data.DummyEntity;

import java.util.ArrayList;
import java.util.List;
//διαβασμα πηγης οταν εχουμε βαση δεδομενων

public class DbHandler {

    public static List<DummyEntity> getDataFromDB(Iterable<DummyEntity> it) {
        ArrayList<DummyEntity> list = new ArrayList<>();
        for (DummyEntity dummy : it) {
            DummyEntity infos = new DummyEntity();
            System.out.println("here: "+ dummy.getX());
            list.add(dummy);
        }
        return list;
    }
}
