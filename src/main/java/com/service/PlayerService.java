package com.service;

import com.pojo.player;

public interface PlayerService {
    public int insert(player player);
    public player selectByTime(String foundTime);
}
