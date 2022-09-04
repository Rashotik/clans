package com.example.clans.Dao;

import com.example.clans.util.Util;
import com.example.clans.model.Clan;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClanDaoImpl implements ClanDao {
    private final Util util;
    public ClanDaoImpl(){
        util = Util.getInstance();
    }
    @Override
    public Clan getClanById(Long id){
        if(id == null)
            return null;
        Clan clan = null;
        try {
            Connection connection = util.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from clans where id = ?");
            statement.setLong(1, id);
            if(statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                resultSet.next();
                clan = new Clan(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getBigDecimal("gold"));
            }
            util.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clan;
    }

    @Override
    public boolean saveClan(Clan clan) {
        if(clan == null)
            return false;
        try {
            Connection connection = util.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO clans(name, gold) VALUES(?, ?)");
            statement.setString(1, clan.getName());
            statement.setBigDecimal(2, clan.getGold());
            if(statement.executeUpdate() == 1) {
                util.closeConnection(connection);
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public List<Clan> getAllClans(){
        Connection connection = util.getConnection();
        if (connection == null)
            return Collections.emptyList();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clans");
            if(statement.executeUpdate() > 0) {
                util.closeConnection(connection);
                List<Clan> clans = new ArrayList<>();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()){
                    clans.add(new Clan(resultSet.getLong("id"),
                                        resultSet.getString("name"),
                                        resultSet.getBigDecimal("gold")));
                }
                return clans;
            }
            return Collections.emptyList();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    @Override
    public boolean increaseGold(Long clanId, BigDecimal gold, Connection connection){
        if(connection == null || clanId == null || gold == null)
            return false;
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE clans SET gold = gold + ? WHERE id = ?;");
            statement.setBigDecimal(1, gold);
            statement.setLong(2, clanId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean decreaseGold(Long clanId, BigDecimal gold, Connection connection){
        if(connection == null || clanId == null || gold == null)
            return false;
        try {
            PreparedStatement statement = connection.prepareStatement("BEGIN;" +
                    "LOCK TABLE clans IN SHARE ROW EXCLUSIVE MODE;" +
                    "UPDATE clans SET gold = gold - ? WHERE id = ? AND gold >= ?;" +
                    "COMMIT;");
            statement.setBigDecimal(1, gold);
            statement.setLong(2, clanId);
            statement.setBigDecimal(3, gold);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
