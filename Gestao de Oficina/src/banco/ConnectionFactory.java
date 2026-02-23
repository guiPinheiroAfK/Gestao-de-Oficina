package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    // A URL aponta para o localhost porque o Docker mapeou a porta 5432 para sua máquina
    private static final String URL = "jdbc:postgresql://localhost:5433/oficina_db";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "admin";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar no banco via Docker: " + e.getMessage());
        }
    }
}