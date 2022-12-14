package com.service;

import com.pojo.sampleBorrow;

import java.util.List;


public interface SampleBorrowService {
    public sampleBorrow selectBySampleID(int sampleID);
    int insert(sampleBorrow sampleBorrow);
    List<sampleBorrow> selectByUserID(int userID);
    List<sampleBorrow> selectByUnit(String belongUnit);
    int update(sampleBorrow sampleBorrow);
}
