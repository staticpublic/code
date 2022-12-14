package com.service;

import com.dao.User_playerMapper;
import com.pojo.user_player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class User_playerServiceImpl implements User_playerService{
    @Autowired
    private User_playerMapper user_playerMapper;

    public void setUser_playerMapper(User_playerMapper user_playerMapper) {
        this.user_playerMapper = user_playerMapper;
    }

    @Override
    public int insert(user_player user_player) {
        return user_playerMapper.insert(user_player);
    }
}
