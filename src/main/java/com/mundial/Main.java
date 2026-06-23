package com.mundial;

import com.mundial.dao.GroupDAO;
import com.mundial.dao.TeamDAO;
import com.mundial.db.SchemaInitializer;
import com.mundial.model.Group;
import com.mundial.model.Team;
import com.mundial.server.ApiServer;

import java.sql.SQLException;

public class Main {

    private static final GroupDAO groupDAO = new GroupDAO();
    private static final TeamDAO teamDAO = new TeamDAO();

    public static void main(String[] args) {
        try {
            SchemaInitializer.createTables();
            poblarDatosSiVacio();
            ApiServer.start(8080);
        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void poblarDatosSiVacio() throws SQLException {
        if (!groupDAO.findAll().isEmpty()) return;

        int gA = groupDAO.save(new Group(1, "A"));
        int gB = groupDAO.save(new Group(2, "B"));
        int gC = groupDAO.save(new Group(3, "C"));
        int gD = groupDAO.save(new Group(4, "D"));
        int gE = groupDAO.save(new Group(5, "E"));
        int gF = groupDAO.save(new Group(6, "F"));
        int gG = groupDAO.save(new Group(7, "G"));
        int gH = groupDAO.save(new Group(8, "H"));
        int gI = groupDAO.save(new Group(9, "I"));
        int gJ = groupDAO.save(new Group(10, "J"));
        int gK = groupDAO.save(new Group(11, "K"));
        int gL = groupDAO.save(new Group(12, "L"));

        teamDAO.save(new Team(0, "México", "México", gA));
        teamDAO.save(new Team(0, "Sudáfrica", "Sudáfrica", gA));
        teamDAO.save(new Team(0, "Corea del Sur", "Corea del Sur", gA));
        teamDAO.save(new Team(0, "Chequia", "República Checa", gA));

        teamDAO.save(new Team(0, "Canadá", "Canadá", gB));
        teamDAO.save(new Team(0, "Bosnia y Herzegovina", "Bosnia y Herzegovina", gB));
        teamDAO.save(new Team(0, "Qatar", "Qatar", gB));
        teamDAO.save(new Team(0, "Suiza", "Suiza", gB));

        teamDAO.save(new Team(0, "Brasil", "Brasil", gC));
        teamDAO.save(new Team(0, "Marruecos", "Marruecos", gC));
        teamDAO.save(new Team(0, "Haití", "Haití", gC));
        teamDAO.save(new Team(0, "Escocia", "Escocia", gC));

        teamDAO.save(new Team(0, "Estados Unidos", "Estados Unidos", gD));
        teamDAO.save(new Team(0, "Paraguay", "Paraguay", gD));
        teamDAO.save(new Team(0, "Australia", "Australia", gD));
        teamDAO.save(new Team(0, "Turquía", "Turquía", gD));

        teamDAO.save(new Team(0, "Alemania", "Alemania", gE));
        teamDAO.save(new Team(0, "Curazao", "Curazao", gE));
        teamDAO.save(new Team(0, "Costa de Marfil", "Costa de Marfil", gE));
        teamDAO.save(new Team(0, "Ecuador", "Ecuador", gE));

        teamDAO.save(new Team(0, "Países Bajos", "Países Bajos", gF));
        teamDAO.save(new Team(0, "Japón", "Japón", gF));
        teamDAO.save(new Team(0, "Suecia", "Suecia", gF));
        teamDAO.save(new Team(0, "Túnez", "Túnez", gF));

        teamDAO.save(new Team(0, "Bélgica", "Bélgica", gG));
        teamDAO.save(new Team(0, "Egipto", "Egipto", gG));
        teamDAO.save(new Team(0, "Irán", "Irán", gG));
        teamDAO.save(new Team(0, "Nueva Zelanda", "Nueva Zelanda", gG));

        teamDAO.save(new Team(0, "España", "España", gH));
        teamDAO.save(new Team(0, "Cabo Verde", "Cabo Verde", gH));
        teamDAO.save(new Team(0, "Arabia Saudita", "Arabia Saudita", gH));
        teamDAO.save(new Team(0, "Uruguay", "Uruguay", gH));

        teamDAO.save(new Team(0, "Francia", "Francia", gI));
        teamDAO.save(new Team(0, "Senegal", "Senegal", gI));
        teamDAO.save(new Team(0, "Irak", "Irak", gI));
        teamDAO.save(new Team(0, "Noruega", "Noruega", gI));

        teamDAO.save(new Team(0, "Argentina", "Argentina", gJ));
        teamDAO.save(new Team(0, "Argelia", "Argelia", gJ));
        teamDAO.save(new Team(0, "Austria", "Austria", gJ));
        teamDAO.save(new Team(0, "Jordania", "Jordania", gJ));

        teamDAO.save(new Team(0, "Portugal", "Portugal", gK));
        teamDAO.save(new Team(0, "R.D. Congo", "República Democrática del Congo", gK));
        teamDAO.save(new Team(0, "Uzbekistán", "Uzbekistán", gK));
        teamDAO.save(new Team(0, "Colombia", "Colombia", gK));

        teamDAO.save(new Team(0, "Inglaterra", "Inglaterra", gL));
        teamDAO.save(new Team(0, "Croacia", "Croacia", gL));
        teamDAO.save(new Team(0, "Ghana", "Ghana", gL));
        teamDAO.save(new Team(0, "Panamá", "Panamá", gL));
    }
}
