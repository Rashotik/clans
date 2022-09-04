package com.example.clans.service;

import com.example.clans.Dao.ClanDao;
import com.example.clans.Dao.ClanDaoImpl;
import com.example.clans.model.Clan;

import java.util.List;

public class ClanServiceImpl implements ClanService {
    ClanDao clanDao = new ClanDaoImpl();

    @Override
    public Clan getClanById(Long id) {
        return clanDao.getClanById(id);
    }

    @Override
    public boolean saveClan(Clan clan) {
        return false;
    }

    @Override
    public List<Clan> getAllClans(){
        return clanDao.getAllClans();
    }

}
