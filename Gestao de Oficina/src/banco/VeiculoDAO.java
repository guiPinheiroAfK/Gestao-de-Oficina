package banco;

import modelo.Veiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VeiculoDAO {

    public void salvar(Veiculo veiculo, String tipo) {
        String sql = "INSERT INTO veiculos (placa, modelo, ano, tipo) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setInt(3, veiculo.getAno());
            stmt.setString(4, tipo);

            stmt.executeUpdate();
            System.out.println("Veiculo salvo no banco com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar veiculo: " + e.getMessage());
        }
    }
}
