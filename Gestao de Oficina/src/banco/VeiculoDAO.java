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

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            return converterResultSet(stmt);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar veiculos: " + e.getMessage());
        }
    }

    // busca por ano
    public List<Veiculo> buscarPorAno(int ano) {
        String sql = "SELECT * FROM veiculos WHERE ano = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ano);
            return converterResultSet(stmt);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por ano: " + e.getMessage());
        }
    }

    public List<Veiculo> buscarPorAnoETipo(int ano, String tipoFiltro) {
        if (tipoFiltro == null) return buscarPorAno(ano);

        String sql = "SELECT * FROM veiculos WHERE ano = ? AND tipo = ?";
        List<Veiculo> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ano);
            stmt.setString(2, tipoFiltro);
            return converterResultSet(stmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Veiculo> buscarOrdenadoPorAno(String tipoFiltro) {
        // Se tipoFiltro for null, busca todos ordenados. Se não, filtra tipo e ordena.
        String sql = (tipoFiltro == null)
                ? "SELECT * FROM veiculos ORDER BY ano ASC"
                : "SELECT * FROM veiculos WHERE tipo = ? ORDER BY ano ASC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (tipoFiltro != null) {
                stmt.setString(1, tipoFiltro);
            }

            return converterResultSet(stmt);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ordenar por ano: " + e.getMessage());
        }
    }

    // busca por modelo (usa LIKE para encontrar nomes parciais)
    public List<Veiculo> buscarPorModelo(String modelo) {
        String sql = "SELECT * FROM veiculos WHERE modelo LIKE ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + modelo + "%");
            return converterResultSet(stmt);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por modelo: " + e.getMessage());
        }
    }

    public List<Veiculo> buscarPorModeloETipo(String modelo, String tipoFiltro) {
        if (tipoFiltro == null) return buscarPorModelo(modelo);

        String sql = "SELECT * FROM veiculos WHERE modelo LIKE ? AND tipo = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + modelo + "%");
            stmt.setString(2, tipoFiltro);
            return converterResultSet(stmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // busca por tipo
    public List<Veiculo> buscarPorTipo(String tipo) {
        String sql = "SELECT * FROM veiculos WHERE tipo = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo);

            return converterResultSet(stmt);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por tipo: " + e.getMessage());
        }
    }

    // para evitar repetir o WHILE(RS.NEXT) toda hora
    private List<Veiculo> converterResultSet(PreparedStatement stmt) throws SQLException {
        List<Veiculo> lista = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                String placa = rs.getString("placa");
                String modelo = rs.getString("modelo");
                int ano = rs.getInt("ano");
                if (tipo.equalsIgnoreCase("carro")) lista.add(new Carro(placa, modelo, ano, tipo));
                else lista.add(new Moto(placa, modelo, ano, tipo));
            }
        }
        return lista;
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
                        return new Carro(placa, modelo, ano, tipo);
                    } else {
                        return new Moto(placa, modelo, ano, tipo);
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