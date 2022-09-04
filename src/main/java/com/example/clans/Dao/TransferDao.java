package com.example.clans.Dao;

import com.example.clans.model.History;
import com.example.clans.model.Transfer;

import java.sql.Connection;
import java.util.List;

public interface TransferDao {
    boolean saveTransfer(Transfer transfer, Connection connection);
    Transfer getTransferById(Long id, Connection connection);
    List<History> getHistoryByEntityName(String name, String entity, Connection connection);
}
