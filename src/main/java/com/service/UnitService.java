package com.service;

import com.pojo.unit;

import java.util.List;

public interface UnitService {
    int insertUnit(unit unit);
    List<unit> selectAll();
    List<unit> selectByName(String unitName);
    unit selectByID(int unitID);
    int updateUnit(unit unit);
    int deleteByID(int unitID);
}
