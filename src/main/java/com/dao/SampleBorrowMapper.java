package com.dao;

import com.pojo.sampleBorrow;

import java.util.List;

public interface SampleBorrowMapper {
    public sampleBorrow selectBySampleID(int sampleID);
    public int insert(sampleBorrow sampleBorrow);
    public List<sampleBorrow> selectByUserID(int userID);
    public List<sampleBorrow> selectByUnit(String belongUnit);
    public int update(sampleBorrow sampleBorrow);
}
