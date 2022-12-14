package com.service;

import com.dao.UnitMapper;
import com.pojo.unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService{
    @Autowired
    private UnitMapper unitMapper;

    public void setUnitMapper(UnitMapper unitMapper) {
        this.unitMapper = unitMapper;
    }

    @Override
    public int insertUnit(unit unit) {
        return unitMapper.insertUnit(unit);
    }

    @Override
    public List<unit> selectAll() {
        return unitMapper.selectAll();
    }

    @Override
    public List<unit> selectByName(String unitName) {
        return unitMapper.selectByName(unitName);
    }

    @Override
    public unit selectByID(int unitID) {
        return unitMapper.selectByID(unitID);
    }

    @Override
    public int updateUnit(unit unit) {
        return unitMapper.updateUnit(unit);
    }

    @Override
    public int deleteByID(int unitID) {
        return unitMapper.deleteByID(unitID);
    }
}
