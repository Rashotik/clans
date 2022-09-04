package com.example.clans.util;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public final class Util {
    private final String dbUserName = "postgres";
    private final String dbPassword = "root";
    private final String dbUrl = "jdbc:postgresql://localhost:5432/clans";

    private static Util instance = null;
    private final List<Connection> freeConnections = new LinkedList<>();
    private final List<Connection> usingConnections = new LinkedList<>();

    public static Util getInstance(){
        if (instance == null)
            instance = new Util();
        return instance;
    }
    private Util() {
        int i = 0;
        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = null;
            while (i < 10){
                connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
                freeConnections.add(connection);
                i++;
            }
            initDataInDB(connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initDataInDB(Connection connection) throws SQLException {

        Statement statement = connection.createStatement();
        statement.execute(
                "BEGIN;\n" +
                        "CREATE TABLE IF NOT EXISTS public.players\n" +
                        "(\n" +
                        "    id SERIAL NOT NULL ,\n" +
                        "    entity_name character varying(50) NOT NULL UNIQUE ,\n" +
                        "    gold numeric,\n" +
                        "    clan_id bigint,\n" +
                        "    PRIMARY KEY (id)\n" +
                        ");\n" +
                        "\n" +
                        "CREATE TABLE IF NOT EXISTS public.clans\n" +
                        "(\n" +
                        "    id SERIAL NOT NULL ,\n" +
                        "    entity_name character varying(50),\n" +
                        "    gold numeric,\n" +
                        "    PRIMARY KEY (id)\n" +
                        ");\n" +
                        "\n" +
                        "CREATE TABLE IF NOT EXISTS public.transfer_checks\n" +
                        "(\n" +
                        "    id SERIAL NOT NULL ,\n" +
                        "    from_id bigint,\n" +
                        "    to_id bigint,\n" +
                        "    gold numeric,\n" +
                        "    sent_from character varying(15),\n" +
                        "    sent_to character varying(15),\n" +
                        "    local_date date,\n" +
                        "    local_time time without time zone,\n" +
                        "    PRIMARY KEY (id)\n" +
                        ");\n" +
                        "END;"
        );
        statement.execute("BEGIN; " +
                "\n INSERT INTO clans(entity_name, gold) VALUES ('wars', 1009);"+
                "\n INSERT INTO clans(entity_name, gold) VALUES ('archers', 1020);"+
                "\n INSERT INTO clans(entity_name, gold) VALUES ('vigings', 2100);"+
                "\n INSERT INTO players(entity_name, gold, clan_id) VALUES ('Joker', 147, 1);"+
                "\n INSERT INTO players(entity_name, gold, clan_id) VALUES ('Harvey', 231, 1);"+
                "\n INSERT INTO players(entity_name, gold, clan_id) VALUES ('Batman', 147, 2);"+
                "\n INSERT INTO transfer_checks(from_id, to_id, gold, sent_from, sent_to, local_date, local_time)" +
                    "VALUES (1, 2, 12.12, 'clan', 'player', '2022-01-23', '17:12:02');"+
                "\n INSERT INTO transfer_checks(from_id, to_id, gold, sent_from, sent_to, local_date, local_time)" +
                    "VALUES (2, 1, 92.10, 'player', 'player', '2022-11-13', '14:01:32');"+
                "\n INSERT INTO transfer_checks(from_id, to_id, gold, sent_from, sent_to, local_date, local_time)" +
                    "VALUES (1, 3, 52.97, 'clan', 'player', '2022-02-10', '12:11:45');"+
                "COMMIT;");
    }
    public synchronized Connection getConnection(){
        while (true) {
            if(!freeConnections.isEmpty()) {
                return freeConnections.get(0);
            }
            if((freeConnections.size() + usingConnections.size()) < 50) {
                return createConnection();
            }
        }
    }
    private Connection createConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            usingConnections.add(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public void closeConnection(Connection connection){
        usingConnections.remove(connection);
        freeConnections.add(connection);
    }
}
