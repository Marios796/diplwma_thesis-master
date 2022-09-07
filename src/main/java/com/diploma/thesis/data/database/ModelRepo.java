package com.diploma.thesis.data.database;

import com.diploma.thesis.data.DummyEntity;
import org.springframework.data.repository.CrudRepository;


public interface ModelRepo  extends CrudRepository<DummyEntity,Integer> {


}
