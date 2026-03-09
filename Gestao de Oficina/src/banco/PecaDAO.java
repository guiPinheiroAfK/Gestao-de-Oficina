package banco;

// DAO - Data Acessa Object

import modelo.Peca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PecaDAO {

    public void salvar(Peca peca) {
        String sql = "INSERT INTO pecas (nome, valor, estoque) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getValor());
            stmt.setInt(3, peca.getEstoque());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar peca: " + e.getMessage());
        }
    }

    public List<Peca> buscarPecas() {
        String sql = "SELECT * FROM pecas ORDER BY nome";
        List<Peca> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Peca p = new Peca(rs.getString("nome"), rs.getDouble("valor"), rs.getInt("estoque"));
                p.setId(rs.getInt("id"));
                lista.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pecas: " + e.getMessage());
        }
        return lista;
    }

    public Peca buscarPorId(int id) {
        String sql = "SELECT * FROM pecas WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Peca p = new Peca(rs.getString("nome"), rs.getDouble("valor"), rs.getInt("estoque"));
                    p.setId(rs.getInt("id"));
                    return p;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar peca: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizar(Peca peca) {
        String sql = "UPDATE pecas SET nome = ?, valor = ?, estoque = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getValor());
            stmt.setInt(3, peca.getEstoque());
            stmt.setInt(4, peca.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar peca: " + e.getMessage());
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM pecas WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar peca: " + e.getMessage());
        }
    }
}
