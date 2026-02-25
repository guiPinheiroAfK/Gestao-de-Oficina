package banco;

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

    public List<Peca> buscarTodas() {
        String sql = "SELECT * FROM pecas";
        List<Peca> catalogo = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Peca p = new Peca(rs.getString("nome"), rs.getDouble("valor"), rs.getInt("estoque"));
                p.setId(rs.getInt("id"));
                catalogo.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar catálogo: " + e.getMessage());
        }
        return catalogo;
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
}