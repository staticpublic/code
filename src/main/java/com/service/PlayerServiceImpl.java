package com.service;

import com.dao.PlayerMapper;
import com.pojo.player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService{
    public void setPlayerMapper(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }

    @Autowired
    PlayerMapper playerMapper;
    @Override
    public int insert(player player) {
        return playerMapper.insert(player);
    }

    @Override
    public player selectByTime(String foundTime) {
        return playerMapper.selectByTime(foundTime);
    }
}
