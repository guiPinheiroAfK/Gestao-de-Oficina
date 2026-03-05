package banco;

// DAO - Data Access Object
//

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
            throw new RuntimeException("Erro ao salvar peça: " + e.getMessage());
        }
    }

    // mesma lógica de buscarVeiculos, só que para peças (buscarPecas)
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
            throw new RuntimeException("Erro ao listar peças: " + e.getMessage());
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
            throw new RuntimeException("Erro ao buscar peça: " + e.getMessage());
        }
        return null;
    }

    public void atualizar(Peca peca) {
        // o SQL usa o ID para saber QUAL registro alterar
        String sql = "UPDATE pecas SET nome = ?, valor = ?, estoque = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getValor());
            stmt.setInt(3, peca.getEstoque());
            stmt.setInt(4, peca.getId()); // Fundamental para o WHERE

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Nenhuma peça encontrada com o ID: " + peca.getId());
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar peça: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM pecas WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar: " + e.getMessage());
        }
    }
}