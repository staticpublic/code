package com.service;

import com.dao.SampleBorrowMapper;
import com.pojo.sampleBorrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleBorrowServiceImpl implements SampleBorrowService {
    @Autowired
    private SampleBorrowMapper sampleBorrowMapper;
    public void setSampleBorrowService(SampleBorrowService sampleBorrowService) {
        this.sampleBorrowMapper = sampleBorrowMapper;
    }

    @Override
    public int insert(sampleBorrow sampleBorrow) {
        return sampleBorrowMapper.insert(sampleBorrow);
    }

    @Override
    public sampleBorrow selectBySampleID(int sampleID) {
        return sampleBorrowMapper.selectBySampleID(sampleID);
    }

    @Override
    public List<sampleBorrow> selectByUserID(int userID) {
        return sampleBorrowMapper.selectByUserID(userID);
    }

    @Override
    public List<sampleBorrow> selectByUnit(String belongUnit) {
        return sampleBorrowMapper.selectByUnit(belongUnit);
    }

    @Override
    public int update(sampleBorrow sampleBorrow) {
        return sampleBorrowMapper.update(sampleBorrow);
    }
}
