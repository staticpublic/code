package com.dao;

import com.pojo.player;

public interface PlayerMapper {
    public int insert(player player);
    public player selectByTime(String foundTime);
}
