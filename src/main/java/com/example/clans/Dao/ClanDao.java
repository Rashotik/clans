package com.example.clans.Dao;

import com.example.clans.model.Clan;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public interface ClanDao {
    Clan getClanById(Long id);
    boolean saveClan(Clan clan);

    List<Clan> getAllClans();

    boolean increaseGold(Long clanId, BigDecimal gold, Connection connection);

    boolean decreaseGold(Long clanId, BigDecimal gold, Connection connection);
}
