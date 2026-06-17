package com.mundial.dao;

import com.mundial.db.ConnectionManager;
import com.mundial.model.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO implements CrudDAO<Group> {
    @Override
    public int save(Group group) throws SQLException {
        String sql = "INSERT INTO GRUPO (nombre) VALUES(?)";
        Connection conn = ConnectionManager.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, group.getName());
        statement.executeUpdate();
        ResultSet result = statement.getGeneratedKeys();
        if (result.next()) return result.getInt(1);
        return 0;
    }

    @Override
    public List<Group> findAll() throws SQLException {
        List<Group> group = new ArrayList<>();
        Connection conn = ConnectionManager.getConnection();
        String sql = "SELECT * FROM GRUPO";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            Group groups = new Group(result.getInt("id"), result.getString("nombre"));
            group.add(groups);
        }
        return group;
    }

    @Override
    public Group findById(int id) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        String sql = "SELECT * FROM GRUPO WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        if (result.next()) return new Group(result.getInt("id"), result.getString("nombre"));
        else return null;
    }
}
