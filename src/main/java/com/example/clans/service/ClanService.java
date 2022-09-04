package com.example.clans.service;

import com.example.clans.model.Clan;

import java.util.List;

public interface ClanService {
    Clan getClanById(Long id);

    boolean saveClan(Clan clan);

    List<Clan> getAllClans();
}
