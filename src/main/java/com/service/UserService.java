package com.service;

import com.pojo.user;

import java.util.List;

public interface UserService {
    int insertUser(user s);
    user selectByIn(String username);
    user selectByRegName(String username);
    int updateInfo(user user);
    user selectByQuestion(user user);
    int changePassword(user user);
    int insertByAdmin(user user);
    List<user> selectAll();
    user selectByID(int userID);
    int updateByID(user user);
    int deleteByID(int userID);
    int updateByTime(user user);
}
