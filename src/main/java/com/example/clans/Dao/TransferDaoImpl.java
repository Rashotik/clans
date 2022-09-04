package com.example.clans.Dao;

import com.example.clans.model.History;
import com.example.clans.model.Transfer;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TransferDaoImpl implements TransferDao{
    @Override
    public boolean saveTransfer(Transfer transfer, Connection connection) {
        if(connection == null || transfer == null)
            return false;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO transfer_checks(from_id, to_id, gold, sent_from, sent_to, local_date, local_time)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?);");
            statement.setLong(1, transfer.getFromId());
            statement.setLong(2, transfer.getToId());
            statement.setBigDecimal(3, transfer.getGold());
            statement.setString(4, transfer.getSentFrom());
            statement.setString(5, transfer.getSentTo());
            statement.setDate(6, Date.valueOf(LocalDate.now()));
            statement.setTime(7, Time.valueOf(LocalTime.now()));
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Transfer getTransferById(Long id, Connection connection) {
        if(connection == null || id == null)
            return null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM transfer_checks where id = ?;");
            statement.setLong(1, id);
            if(statement.execute()){
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    return new Transfer(resultSet.getLong("id"),
                                        resultSet.getLong("from_id"),
                                        resultSet.getLong("to_id"),
                                        resultSet.getBigDecimal("gold"),
                                        resultSet.getString("sent_from"),
                                        resultSet.getString("sent_to"),
                                        resultSet.getDate("local_date"),
                                        resultSet.getTime("local_time"));
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<History> getHistoryByEntityName(String name, String entity, Connection connection) {
        if(connection == null || name == null)
            return null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT DISTINCT t.sent_from, " +
                            "CASE WHEN t.sent_from LIKE 'player' THEN (select e.entity_name from players as e where e.id = t.from_id) " +
                                "WHEN t.sent_from LIKE 'clan' THEN (select e.entity_name from clans as e where e.id = t.from_id)" +
                            "END as from_name, " +
                            "t.sent_to, " +
                            "CASE WHEN t.sent_to LIKE 'player' THEN (select e.entity_name from players as e where e.id = t.to_id) " +
                            "WHEN t.sent_to LIKE 'clan' THEN (select e.entity_name from clans as e where e.id = t.to_id)" +
                            "END as to_name, " +
                            "t.gold, " +
                            "t.local_date, " +
                            "t.local_time " +
                            "FROM transfer_checks as t\n" +
                            "join replace_entity as c on t.from_id = c.id or t.to_id = c.id\n" +
                            "WHERE c.entity_name = ? and \n" +
                            "((t.from_id = c.id and t.sent_from = ?)\n" +
                            " or (t.to_id = c.id and t.sent_to = ?))");
            statement.setString(1, name);
            statement.setString(2, entity);
            statement.setString(3, entity);
            return getTransfers(statement, entity);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    private List<History> getTransfers(PreparedStatement statement, String entity) throws SQLException {
        Statement statement1 = statement.getConnection().createStatement();
        if(statement1.execute(statement.toString()
                .replaceAll("replace_entity", entity+"s")
                .replace("replace_entity", entity+"s"))){
            List<History> transfers = new ArrayList<>();
            ResultSet resultSet = statement1.getResultSet();
            while (resultSet.next()) {
                transfers.add(new History(resultSet.getString("sent_from"),
                        resultSet.getString("from_name"),
                        resultSet.getString("sent_to"),
                        resultSet.getString("to_name"),
                        resultSet.getBigDecimal("gold"),
                        resultSet.getDate("local_date"),
                        resultSet.getTime("local_time")));
            }
            return transfers;
        }
        return null;
    }
}
