package com.example.clans.Dao;

import java.math.BigDecimal;
import java.sql.Connection;

public interface PlayerDao {
    boolean increaseGold(Long clanId, BigDecimal gold, Connection connection);

    boolean decreaseGold(Long clanId, BigDecimal gold, Connection connection);
}
