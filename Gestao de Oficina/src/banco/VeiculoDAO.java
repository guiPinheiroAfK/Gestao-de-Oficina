package banco;

import modelo.Veiculo;
import modelo.Carro;
import modelo.Moto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Veiculo> buscarTodos() {
        String sql = "SELECT * FROM veiculos";
        List<Veiculo> veiculosL = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                String placa = rs.getString("placa");
                String modelo = rs.getString("modelo");
                int ano = rs.getInt("ano");

                // lógica para instanciar a classe correta
                if (tipo.equalsIgnoreCase("carro")) {
                    veiculosL.add(new Carro(placa, modelo, ano));
                } else
                    veiculosL.add(new Moto(placa, modelo, ano));
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao listar veiculos: " + e.getMessage());
            }
        return veiculosL;
    }

}
