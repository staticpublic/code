package com.dao;

import com.pojo.sample;
import org.apache.ibatis.javassist.tools.reflect.Sample;

import java.util.List;

public interface SampleMapper {
    public List<Sample> selectAll();
    public List<Sample> selectAllByAdmin();
    public int updateSample(sample s);
    public sample selectAllByID(int sampleID);
    public int deleteByID(int sampleID);
    public List<sample> selectByUnit(String belongUnit);
    public int insertSample(sample sample);
    //标本名称、出土地、标本材质、所属单位
    public List<sample> selectByName(String name);
    public List<sample> selectByPlace(String birthplace);
    public List<sample> selectByMaterial(String material);
    public List<sample> selectByVagueUnit(String belongUnit);
    public List<sample> selectBorrow(String belongUnit);

}
