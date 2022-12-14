package com.service;

import com.dao.UserMapper;
import com.pojo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    public int insertUser(user u){
        return userMapper.insertUser(u);
    }

    @Override
    public user selectByIn(String username) {
        return userMapper.selectByIn(username);
    }

    @Override
    public user selectByRegName(String username) {
        return userMapper.selectByRegName(username);
    }

    @Override
    public int updateInfo(user user) {
        return userMapper.updateInfo(user);
    }

    @Override
    public user selectByQuestion(user user) {
        return userMapper.selectByQuestion(user);
    }

    @Override
    public int changePassword(user user) {
        return userMapper.changePassword(user);
    }

    @Override
    public int insertByAdmin(user user) {
        return userMapper.insertByAdmin(user);
    }

    @Override
    public List<user> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public user selectByID(int userID) {
        return userMapper.selectByID(userID);
    }

    @Override
    public int updateByID(user user) {
        return userMapper.updateByID(user);
    }

    @Override
    public int deleteByID(int userID) {
        return userMapper.deleteByID(userID);
    }

    @Override
    public int updateByTime(user user) {
        return userMapper.updateByTime(user);
    }

    public void setUserMapper(String userMapper) {

    }

}
