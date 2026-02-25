package banco;

import modelo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    // cadastra os veículos
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

    // busca eles para serem listados
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

    // deleta veículos por placa:
    // "DELETE FROM: veiculos WHERE placa = ?"
    public void deletar(String placa) {
        String sql = "DELETE FROM veiculos WHERE placa = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, placa);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Veiculo deletado com sucesso!");
            } else
                System.out.println("Placa não encontrada veiculo!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar: " + e.getMessage());
        }
    }

    public Veiculo buscarPorPlaca(String placa) {
        String sql = "SELECT * FROM veiculos WHERE placa = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipo");
                    String modelo = rs.getString("modelo");
                    int ano = rs.getInt("ano");
                    if (tipo.equalsIgnoreCase("carro")) {
                        return new Carro(placa, modelo, ano);
                    } else {
                        return new Moto(placa, modelo, ano);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veículo: " + e.getMessage());
        }
        return null;
    }

    // nosso querido UPTADE!!!
    // caso erre o nome, ano ou placa, não podendo alterar o tipo (ainda hehe, mas é simples de implementar)
    public void atualizar(Veiculo veiculo) {
        String sql = "UPDATE veiculos SET modelo = ?, ano = ? WHERE placa = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getModelo());
            stmt.setInt(2, veiculo.getAno());
            stmt.setString(3, veiculo.getPlaca());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Veiculo atualizado com sucesso!");
            } else
                System.out.println("Nenhum veículo encontrada com a placa" + veiculo.getPlaca());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar veículo: " + e.getMessage());
        }
    }
}