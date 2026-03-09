package banco;

// DAO - Data Acess Object

import modelo.Moto;
import modelo.Carro;
import modelo.Veiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    private static final String URL = "jdbc:postgresql://localhost:5433/oficina_db";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "admin";

    public void salvar(Veiculo v) {
        String sql = "INSERT INTO veiculos (placa, marca, modelo, ano, tipo) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getPlaca());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getModelo());
            ps.setInt(4, v.getAno());
            ps.setString(5, v.getTipo());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar veiculo: " + e.getMessage());
        }
    }

    public boolean atualizar(Veiculo v, String placaAntiga) {
        String sql = "UPDATE veiculos SET placa = ?, marca = ?, modelo = ?, ano = ? WHERE placa = ?";
        try (Connection con = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getPlaca());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getModelo());
            ps.setInt(4, v.getAno());
            ps.setString(5, placaAntiga);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar veiculo: " + e.getMessage());
        }
    }

    public boolean deletar(String placa) {
        String sql = "DELETE FROM veiculos WHERE placa = ?";
        try (Connection con = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, placa);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar veiculo: " + e.getMessage());
        }
    }

    public List<Veiculo> buscarVeiculos() {
        String sql = "SELECT * FROM veiculos ORDER BY tipo, placa";
        return executarConsulta(sql);
    }

    public Veiculo buscarPorPlaca(String placa) {
        String sql = "SELECT * FROM veiculos WHERE placa = ?";
        List<Veiculo> resultado = executarConsulta(sql, placa);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public List<Veiculo> buscarPorTipo(String tipo) {
        String sql = "SELECT * FROM veiculos WHERE tipo = ? ORDER BY placa";
        return executarConsulta(sql, tipo);
    }

    public List<Veiculo> buscarPorAno(int ano) {
        String sql = "SELECT * FROM veiculos WHERE ano = ? ORDER BY placa";
        return executarConsulta(sql, ano);
    }


    public List<Veiculo> buscarPorAnoETipo(int ano, String tipoFiltro) {
        if (tipoFiltro == null || tipoFiltro.isEmpty() || tipoFiltro.equals("AMBOS")) {
            return buscarPorAno(ano);
        }

        String sql = "SELECT * FROM veiculos WHERE ano = ? AND tipo = ? ORDER BY placa";
        return executarConsulta(sql, ano, tipoFiltro);
    }

    public List<Veiculo> buscarPorModelo(String modelo) {
        String sql = "SELECT * FROM veiculos WHERE modelo ILIKE ? ORDER BY placa";
        return executarConsulta(sql, "%" + modelo + "%");
    }

    public List<Veiculo> buscarPorModeloETipo(String modelo, String tipoFiltro) {
        // Se o tipo for "Ambos" (null ou vazio), usamos a busca geral por modelo
        if (tipoFiltro == null || tipoFiltro.isEmpty() || tipoFiltro.equals("AMBOS")) {
            return buscarPorModelo(modelo);
        }

        String sql = "SELECT * FROM veiculos WHERE modelo ILIKE ? AND tipo = ? ORDER BY placa";
        return executarConsulta(sql, "%" + modelo + "%", tipoFiltro);
    }

    public List<Veiculo> buscarPorMarca(String marca) {
        String sql = "SELECT * FROM veiculos WHERE marca ILIKE ? ORDER BY placa";
        return executarConsulta(sql, "%" + marca + "%");
    }

    public List<Veiculo> buscarPorMarcaETipo(String marca, String tipoFiltro) {
        // se o tipo for "Ambos" usa a busca geral por marca
        if (tipoFiltro == null || tipoFiltro.isEmpty() || tipoFiltro.equals("AMBOS")) {
            return buscarPorMarca(marca);
        }

        String sql = "SELECT * FROM veiculos WHERE marca ILIKE ? AND tipo = ? ORDER BY placa";

        return executarConsulta(sql, "%" + marca + "%", tipoFiltro);
    }

    public List<Veiculo> buscarOrdenadoPorAno() {
        String sql = "SELECT * FROM veiculos ORDER BY ano ASC";
        return executarConsulta(sql);
    }


    private List<Veiculo> executarConsulta(String sql, Object... parametros) { // Object... para receber nenhum, um ou vários objetos
        // pode passar qualquer coisa: String, Integer, Double, etc
        List<Veiculo> lista = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tipo = rs.getString("tipo");
                    String placa = rs.getString("placa");
                    String marca = rs.getString("marca");
                    String modelo = rs.getString("modelo");
                    int ano = rs.getInt("ano");

                    if ("CARRO".equalsIgnoreCase(tipo)) {
                        lista.add(new Carro(placa, marca, modelo, ano));
                    } else {
                        lista.add(new Moto(placa, marca, modelo, ano));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro na consulta: " + e.getMessage());
        }
        return lista;
    }
}