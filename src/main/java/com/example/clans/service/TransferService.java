package com.example.clans.service;

import com.example.clans.model.History;
import com.example.clans.model.Transfer;

import java.util.List;

public interface TransferService {
    Transfer getTransferById(Long id);
    boolean transferGoldAndSaveCheck(Transfer transfer);
    List<History> getTransfersByEntityName(String name, String Entity);
}
