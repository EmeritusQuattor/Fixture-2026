package com.mundial.dao;

import com.mundial.db.ConnectionManager;
import com.mundial.model.Match;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchDAO implements CrudDAO<Match> {
    @Override
    public int save(Match entity) throws SQLException {
        String sql = "INSERT INTO PARTIDO (local_id, visitante_id, grupo_id, fase, fecha, goles_local, goles_visitante, estado) VALUES (?,?,?,?,?,?,?,?)";
        Connection conn = ConnectionManager.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1,entity.getLocalId());
        statement.setInt(2,entity.getVisitorId());
        statement.setInt(3,entity.getGroupId());
        statement.setString(4,entity.getPhase());
        statement.setString(5,entity.getDate());
        statement.setInt(6,entity.getLocalGoals());
        statement.setInt(7,entity.getVisitorGoals());
        statement.setString(8,entity.getStatus());
        statement.executeUpdate();
        ResultSet result = statement.getGeneratedKeys();
        if (result.next()) return  result.getInt(1);
        return 0;
    }

    @Override
    public List<Match> findAll() throws SQLException {
        List<Match> match = new ArrayList<>();
        Connection conn = ConnectionManager.getConnection();
        String sql = "SELECT * FROM PARTIDO";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while(result.next()){
            Match matches = new Match(result.getInt("id"),
                    result.getInt("visitante_id"),
                    result.getInt("local_id"),
                    result.getInt("grupo_id"),
                    result.getString("fase"),
                    result.getString("fecha"),
                    result.getInt("goles_local"),
                    result.getInt("goles_visitante"),
                    result.getString("estado"));
            match.add(matches);
        }
        return match;
    }

    @Override
    public Match findById(int id) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        String sql = "SELECT * FROM PARTIDO WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            return new Match(result.getInt("id"),
                    result.getInt("visitante_id"),
                    result.getInt("local_id"),
                    result.getInt("grupo_id"),
                    result.getString("fase"),
                    result.getString("fecha"),
                    result.getInt("goles_local"),
                    result.getInt("goles_visitante"),
                    result.getString("estado"));
        }
        return null;
    }
}
