import java.sql.*;

public class CheckDB {
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:mundial2026.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as c FROM GRUPO");
        rs.next();
        System.out.println("Grupos: " + rs.getInt("c"));
        rs = stmt.executeQuery("SELECT COUNT(*) as c FROM EQUIPO");
        rs.next();
        System.out.println("Equipos: " + rs.getInt("c"));
        rs = stmt.executeQuery("SELECT COUNT(*) as c FROM PARTIDO");
        rs.next();
        System.out.println("Partidos: " + rs.getInt("c"));
        rs = stmt.executeQuery("SELECT * FROM EQUIPO LIMIT 5");
        while (rs.next()) {
            System.out.println("  " + rs.getInt("id") + ": " + rs.getString("nombre") + " (" + rs.getString("pais") + ")");
        }
        conn.close();
    }
}
