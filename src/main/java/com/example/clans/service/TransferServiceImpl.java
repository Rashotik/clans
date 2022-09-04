package com.example.clans.service;

import com.example.clans.Dao.*;
import com.example.clans.model.History;
import com.example.clans.model.Transfer;
import com.example.clans.util.Util;

import java.sql.Connection;
import java.util.List;

public class TransferServiceImpl implements TransferService{
    private final TransferDao transferDao;
    private final PlayerDao playerDao;
    private final ClanDao clanDao;
    private final Util util;

    public TransferServiceImpl(){
        this.transferDao = new TransferDaoImpl();
        this.playerDao = new PlayerDaoImpl();
        this.clanDao = new ClanDaoImpl();
        this.util = Util.getInstance();
    }

    @Override
    public Transfer getTransferById(Long id) {
        Connection connection = util.getConnection();
        Transfer transfer = transferDao.getTransferById(id, connection);
        util.closeConnection(connection);
        return transfer;
    }

    @Override
    public boolean transferGoldAndSaveCheck(Transfer transfer) {
        if(transfer == null)
            return false;
        Connection connection = util.getConnection();
        System.out.println(transfer.getSentFrom());
        switch (transfer.getSentFrom()) {
            case "clan":
                if (!fromIsClan(transfer, connection)) {
                    util.closeConnection(connection);
                    return false;
                }
                break;
            case "player":
                if (!fromIsPlayer(transfer, connection)) {
                    util.closeConnection(connection);
                    return false;
                }
                break;
            case "task":
                if (!fromIsTask(transfer, connection)) {
                    util.closeConnection(connection);
                    return false;
                }
                break;
        }
        util.closeConnection(connection);
        return true;
    }

    private boolean fromIsClan(Transfer transfer, Connection connection){
        if (clanDao.decreaseGold(transfer.getFromId(), transfer.getGold(), connection)) {
            switch (transfer.getSentTo()) {
                case "clan":
                    if (!toIsClan(transfer, connection)) {
                        clanDao.increaseGold(transfer.getFromId(), transfer.getGold(), connection);
                        return false;
                    }
                    break;
                case "player":
                    if (!toIsPlayer(transfer, connection)) {
                        clanDao.increaseGold(transfer.getFromId(), transfer.getGold(), connection);
                        return false;
                    }
                    break;
                case "item":
                    if (!transferDao.saveTransfer(transfer, connection)) {
                        clanDao.decreaseGold(transfer.getToId(), transfer.getGold(), connection);
                        System.out.println("Unable save check");
                        return false;
                    }
                    break;
                default: return false;
            }
            return true;
        }else {
            System.out.println("Unable transfer from clan");
            return false;
        }
    }
    private boolean fromIsPlayer(Transfer transfer, Connection connection){
        if (playerDao.decreaseGold(transfer.getFromId(), transfer.getGold(), connection)) {
            switch (transfer.getSentTo()) {
                case "clan":
                    if (!toIsClan(transfer, connection)) {
                        playerDao.increaseGold(transfer.getFromId(), transfer.getGold(), connection);
                        return false;
                    }
                    break;
                case "player":
                    if (!toIsPlayer(transfer, connection)) {
                        playerDao.increaseGold(transfer.getFromId(), transfer.getGold(), connection);
                        return false;
                    }
                    break;
                case "item":
                    if (!transferDao.saveTransfer(transfer, connection)) {
                        playerDao.increaseGold(transfer.getFromId(), transfer.getGold(), connection);
                        System.out.println("Unable save check");
                        return false;
                    }
                    break;
                default: return false;
            }
            return true;
        }else {
            System.out.println("Unable transfer from player");
            return false;
        }
    }
    private boolean fromIsTask(Transfer transfer, Connection connection){
        if(transfer.getSentTo().equals("clan")) {
            return toIsClan(transfer, connection);
        }
        else if(transfer.getSentTo().equals("player")) {
            return toIsPlayer(transfer, connection);
        }
        return false;
    }
    private boolean toIsClan(Transfer transfer, Connection connection){
        if(clanDao.increaseGold(transfer.getToId(), transfer.getGold(), connection)){
            if (!transferDao.saveTransfer(transfer, connection)) {
                clanDao.decreaseGold(transfer.getToId(), transfer.getGold(), connection);
                System.out.println("Unable save check");
                return false;
            }
            return true;
        }else {
            System.out.println("Unable transfer to clan");
            return false;
        }
    }
    private boolean toIsPlayer(Transfer transfer, Connection connection){
        if(playerDao.increaseGold(transfer.getToId(), transfer.getGold(), connection)){
            if (!transferDao.saveTransfer(transfer, connection)) {
                playerDao.decreaseGold(transfer.getToId(), transfer.getGold(), connection);
                System.out.println("Unable save check");
                return false;
            }
            return true;
        }else {
            System.out.println("Unable transfer to player");
            return false;
        }
    }

    @Override
    public List<History> getTransfersByEntityName(String name, String entity) {
        Connection connection = util.getConnection();
        List<History> transfers = transferDao.getHistoryByEntityName(name, entity, connection);
        util.closeConnection(connection);
        return transfers;
    }
}
