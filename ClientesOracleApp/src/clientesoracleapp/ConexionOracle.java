
package clientesoracleapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "SYSTEM"; // cambia por tu usuario de Oracle
    private static final String PASS = "Oracle"; // cambia por tu contraseña

    public static Connection conectar() {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Conexión exitosa a Oracle");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("❌ Error al conectar a Oracle: " + e.getMessage());
        }
        return con;
    }
}