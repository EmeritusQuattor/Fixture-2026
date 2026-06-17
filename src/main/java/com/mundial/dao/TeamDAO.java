package com.mundial.dao;

import com.mundial.db.ConnectionManager;
import com.mundial.model.Group;
import com.mundial.model.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO implements CrudDAO<Team> {
    @Override
    public int save(Team entity) throws SQLException {
        String sql = "INSERT INTO EQUIPO (nombre, pais, grupo_id) VALUES (?,?,?)";
        Connection conn = ConnectionManager.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getCountry());
        statement.setInt(3, entity.getGroupId());
        statement.executeUpdate();
        ResultSet result = statement.getGeneratedKeys();
        if (result.next()) return result.getInt(1);
        return 0;
    }

    @Override
    public List<Team> findAll() throws SQLException {
        List<Team> team = new ArrayList<>();
        Connection conn = ConnectionManager.getConnection();
        String sql = "SELECT * FROM EQUIPO";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            Team teams = new Team(result.getInt("id"), result.getString("nombre"),
                    result.getString("pais"), result.getInt("grupo_id"));
            team.add(teams);
        }
        return team;

    }

    @Override
    public Team findById(int id) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        String sql = "SELECT * FROM EQUIPO WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        if (result.next()) return new Group(result)
        return null;
    }
}
