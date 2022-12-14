package com.dao;

import com.pojo.user;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public int insertUser(user s);
    public user selectByIn(String username);
    public user selectByRegName(String username);
    public int updateInfo(user user);
    public user selectByQuestion(user user);
    public int changePassword(user user);
    public int insertByAdmin(user user);
    public List<user> selectAll();
    public user selectByID(int userID);
    public int updateByID(user user);
    public int deleteByID(int userID);
    public int updateByTime(user user);


}
