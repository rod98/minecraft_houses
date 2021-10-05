package org.mine;

import java.io.*;
import java.net.IDN;
import java.util.*;
import java.sql.*;

public class SQLTalker {
    private Connection conn;

    public List<IdNamePair> getHouseList(int playerId) throws Exception {
        List<IdNamePair>  houselist = new ArrayList<IdNamePair>();
        String            sstring   = "SELECT house AS id, hname FROM playerinhouse ph JOIN houses ho ON ho.id = ph.house WHERE player = ?";
        PreparedStatement statement = conn.prepareStatement(sstring);
        statement.setInt(1, playerId);
        ResultSet         quresult  = statement.executeQuery();

        while (quresult.next()) {
            String hname = quresult.getString("hname");
            int    hoid  = quresult.getInt("id");

            houselist.add(new IdNamePair(hoid, hname));
        }

        return houselist;
    }

    public List<IdNamePair> getPlayerList() throws Exception {
        List<IdNamePair> result = new ArrayList<IdNamePair>();

        String    query = "SELECT id, pname FROM players";
        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(query);

        while (rs.next()) {
            String pname = rs.getString("pname");
            int    plaid = rs.getInt("id");

            result.add(new IdNamePair(plaid, pname));
        }

        return result;
    }

    public List<IdNamePair> getEnchInHouseList(int house_id) throws Exception {
        String statement_string =
            "WITH hench AS " +
            "(SELECT id, fk_ench FROM enchinhouse eh WHERE eh.fk_house = ?)" +
            "SELECT hench.id AS record_id, e.id AS ench_id, ename " +
            "FROM hench RIGHT JOIN enchantments e ON e.id = hench.fk_ench " +
            "ORDER BY e.ename ";

        PreparedStatement statement = conn.prepareStatement(statement_string);
        List<IdNamePair>  elist = new ArrayList<IdNamePair>();

        statement.setInt(1, house_id);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String ename = rs.getString("ename");
            int    recid = rs.getInt   ("record_id");
            int    encid = rs.getInt   ("ench_id");

            elist.add(new IdNamePair(recid, new IdNamePair(encid, ename)));
        }

        return elist;
    }

    public void insertPlayer(String name) throws Exception {
        String statement_string = "INSERT INTO Players (pname) VALUES (?)";
        
        PreparedStatement statement = conn.prepareStatement(statement_string);
        statement.setString(1, name.trim());
        statement.execute();
    }

    public void insertHouse(String name, int owner_id, int x, int y, int z) throws Exception {
        String house_string = "INSERT INTO houses        (hname, pos_x, pos_y, pos_z) VALUES (?, ?, ?, ?)";
        String owner_string = "INSERT INTO playerinhouse (house, player)              VALUES (?, ?)";
        String getid_string = "SELECT id FROM houses WHERE hname = ?";
        PreparedStatement house_statement = conn.prepareStatement(house_string);
        PreparedStatement owner_statement = conn.prepareStatement(owner_string);
        PreparedStatement getid_statement = conn.prepareStatement(getid_string);

        name = name.trim();

        house_statement.setString(1, name);
        house_statement.setInt   (2, x);
        house_statement.setInt   (3, y);
        house_statement.setInt   (4, z);

        house_statement.execute();
        
        getid_statement.setString(1, name);
        ResultSet rs = getid_statement.executeQuery();
        rs.next();
        int newid = rs.getInt("id");

        owner_statement.setInt(1, newid);
        owner_statement.setInt(2, owner_id);

        owner_statement.execute();
    }

    public void insertEnchantment(String name, int max_level) throws Exception {
        String statement_string = "INSERT INTO enchantments (ename, max_level) VALUES (?, ?)";
        
        PreparedStatement statement = conn.prepareStatement(statement_string);
        statement.setString(1, name.trim());
        statement.setInt   (2, max_level);
        statement.execute();
    }

    public int insertEnchInHouse(int houseId, int enchId) throws Exception {
        String statement_string = "INSERT INTO enchinhouse (fk_house, fk_ench) VALUES (?, ?)";
        PreparedStatement statement = conn.prepareStatement(statement_string);

        statement.setInt (1, houseId);
        statement.setInt (2, enchId);
        statement.execute();

        statement_string = "SELECT id FROM enchinhouse WHERE fk_house = ? AND fk_ench = ?";
        statement = conn.prepareStatement(statement_string);
        statement.setInt (1, houseId);
        statement.setInt (2, enchId);

        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt("id");
    }

    public void deleteEnchInHouse(int record_id) throws Exception {
        String statement_string = "DELETE FROM enchinhouse WHERE id = ?";
        
        PreparedStatement statement = conn.prepareStatement(statement_string);
        statement.setInt (1, record_id);
        statement.execute();
    }

    public SQLTalker(CredentialsIO credio) throws Exception {
        init(credio);
    }

    public void init(CredentialsIO credio) throws Exception {
        credio.read();
        conn = DriverManager.getConnection(credio.url(), credio.properties());
    }
}
