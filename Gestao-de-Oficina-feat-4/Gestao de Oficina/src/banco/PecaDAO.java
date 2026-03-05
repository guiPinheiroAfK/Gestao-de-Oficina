package banco;

import modelo.Peca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PecaDAO {

    private void garantirTabelaPecas() {
        String sql = """
                CREATE TABLE IF NOT EXISTS pecas (
                    id SERIAL PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    valor NUMERIC(10,2) NOT NULL CHECK (valor >= 0),
                    estoque INTEGER NOT NULL CHECK (estoque >= 0)
                )
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao garantir tabela pecas: " + e.getMessage());
        }
    }

    public void salvar(Peca peca) {
        garantirTabelaPecas();
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
        garantirTabelaPecas();
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
        garantirTabelaPecas();
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
        garantirTabelaPecas();
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
        garantirTabelaPecas();
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
