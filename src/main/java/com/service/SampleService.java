package com.service;

import com.pojo.sample;
import org.apache.ibatis.javassist.tools.reflect.Sample;

import java.util.List;

public interface SampleService {
    List<Sample> selectAll();
    List<Sample> selectAllByAdmin();
    int updateSample(sample s);
    sample selectAllByID(int sampleID);
    int deleteByID(int sampleID);
    List<sample> selectByUnit(String belongUnit);
    int insertSample(sample sample);
    List<sample> selectByName(String name);
    List<sample> selectByPlace(String birthplace);
    List<sample> selectByMaterial(String material);
    List<sample> selectByVagueUnit(String belongUnit);
    List<sample> selectBorrow(String belongUnit);

}
