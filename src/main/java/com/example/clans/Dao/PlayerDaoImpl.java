package com.example.clans.Dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerDaoImpl implements PlayerDao{
    @Override
    public boolean increaseGold(Long playerId, BigDecimal gold, Connection connection){
        if(connection == null || playerId == null || gold == null)
            return false;
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE players SET gold = gold + ? WHERE id = ?;");
            statement.setBigDecimal(1, gold);
            statement.setLong(2, playerId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean decreaseGold(Long playerId, BigDecimal gold, Connection connection){
        if(connection == null || playerId == null || gold == null)
            return false;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE players SET gold = gold - ? WHERE id = ? AND gold >= ?;");
            statement.setBigDecimal(1, gold);
            statement.setLong(2, playerId);
            statement.setBigDecimal(3, gold);
            int i = statement.executeUpdate();
            System.out.println(i);
            return i > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
