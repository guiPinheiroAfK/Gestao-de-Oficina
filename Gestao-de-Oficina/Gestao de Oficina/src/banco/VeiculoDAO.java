package banco;

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

    public List<Veiculo> buscarVeiculos() {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT placa, marca, modelo, ano, tipo FROM veiculos ORDER BY tipo, placa";
        try (Connection con = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                if ("CARRO".equals(tipo)) {
                    lista.add(new modelo.Carro(
                            rs.getString("placa"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getInt("ano")
                    ));
                } else {
                    lista.add(new modelo.Moto(
                            rs.getString("placa"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getInt("ano")
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veiculos: " + e.getMessage());
        }
        return lista;
    }

    public Veiculo buscarPorPlaca(String placa) {
        String sql = "SELECT placa, marca, modelo, ano, tipo FROM veiculos WHERE placa = ?";
        try (Connection con = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, placa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                if ("CARRO".equals(tipo)) {
                    return new modelo.Carro(
                            rs.getString("placa"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getInt("ano")
                    );
                }
                return new modelo.Moto(
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veiculo: " + e.getMessage());
        }
        return null;
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

    public boolean atualizar(Veiculo v) {
        String sql = "UPDATE veiculos SET marca = ?, modelo = ?, ano = ? WHERE placa = ?";
        try (Connection con = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getMarca());
            ps.setString(2, v.getModelo());
            ps.setInt(3, v.getAno());
            ps.setString(4, v.getPlaca());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar veiculo: " + e.getMessage());
        }
    }

    public List<Veiculo> buscarPorTipo(String tipoFiltro) {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT placa, marca, modelo, ano, tipo FROM veiculos WHERE tipo = ? ORDER BY placa";
        try (Connection con = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tipoFiltro);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if ("CARRO".equals(tipoFiltro)) {
                    lista.add(new modelo.Carro(
                            rs.getString("placa"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getInt("ano")
                    ));
                } else {
                    lista.add(new modelo.Moto(
                            rs.getString("placa"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getInt("ano")
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veiculos por tipo: " + e.getMessage());
        }
        return lista;
    }
}
