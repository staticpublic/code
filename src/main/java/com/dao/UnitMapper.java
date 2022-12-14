package com.dao;

import com.pojo.unit;

import java.util.List;

public interface UnitMapper {
    public int insertUnit(unit unit);
    public List<unit> selectAll();
    public List<unit> selectByName(String unitName);
    public unit selectByID(int unitID);
    public int updateUnit(unit unit);
    public int deleteByID(int unitID);
}
