package com.service;

import com.dao.SampleMapper;
import com.pojo.sample;
import org.apache.ibatis.javassist.tools.reflect.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleServiceImpl implements SampleService{
    @Autowired
    private SampleMapper sampleMapper;

    public void setSampleMapper(String SampleMapper) {

    }

    @Override
    public List<Sample> selectAll() {
        return sampleMapper.selectAll();

    }

    @Override
    public List<Sample> selectAllByAdmin() {
        return sampleMapper.selectAllByAdmin();
    }

    @Override
    public int updateSample(sample s) {
        return sampleMapper.updateSample(s);
    }

    @Override
    public sample selectAllByID(int sampleID) {
        return sampleMapper.selectAllByID(sampleID);
    }

    @Override
    public int deleteByID(int sampleID) {
        return sampleMapper.deleteByID(sampleID);
    }

    @Override
    public List<sample> selectByUnit(String belongUnit) {
        return sampleMapper.selectByUnit(belongUnit);
    }

    @Override
    public int insertSample(sample sample) {
        return sampleMapper.insertSample(sample);
    }

    @Override
    public List<sample> selectByName(String name) {
        return sampleMapper.selectByName(name);
    }

    @Override
    public List<sample> selectByMaterial(String material) {
        return sampleMapper.selectByMaterial(material);
    }

    @Override
    public List<sample> selectByPlace(String birthplace) {
        return sampleMapper.selectByPlace(birthplace);
    }

    @Override
    public List<sample> selectByVagueUnit(String belongUnit) {
        return sampleMapper.selectByVagueUnit(belongUnit);
    }

    @Override
    public List<sample> selectBorrow(String belongUnit) {
        return sampleMapper.selectBorrow(belongUnit);
    }
}
